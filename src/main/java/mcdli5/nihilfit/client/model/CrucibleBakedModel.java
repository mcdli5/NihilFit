package mcdli5.nihilfit.client.model;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import mcdli5.nihilfit.block.crucible.CrucibleTile;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.geometry.ISimpleModelGeometry;
import net.minecraftforge.client.model.pipeline.BakedQuadBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public final class CrucibleBakedModel extends BakedModelWrapper<IBakedModel> implements IDynamicBakedModel {
    public CrucibleBakedModel(IBakedModel originalModel) {
        super(originalModel);
    }

    private static Vec3d v(double x, double y, double z) {
        return new Vec3d(x, y, z);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        BlockState content = extraData.getData(CrucibleTile.CONTENT);
        Integer level = extraData.getData(CrucibleTile.LEVEL);

        List<BakedQuad> retQuads = new ArrayList<>(this.originalModel.getQuads(state, side, rand, extraData));

        if (level != null && level > 0) {
            TextureAtlasSprite texture = this.getContentModel(content).getParticleTexture();
            List<BakedQuad> contentQuads = new ArrayList<>();

            double minY = 0.25;
            double maxY = minY + (level * 0.0625);
            double min = 0.125;
            double max = 0.875;

            contentQuads.add(createQuad(v(min, maxY, min), v(min, maxY, max), v(max, maxY, max), v(max, maxY, min), texture));
            contentQuads.add(createQuad(v(min, minY, min), v(max, minY, min), v(max, minY, max), v(min, minY, max), texture));
            contentQuads.add(createQuad(v(max, maxY, max), v(max, minY, max), v(max, minY, min), v(max, maxY, min), texture));
            contentQuads.add(createQuad(v(min, maxY, min), v(min, minY, min), v(min, minY, max), v(min, maxY, max), texture));
            contentQuads.add(createQuad(v(max, maxY, min), v(max, minY, min), v(min, minY, min), v(min, maxY, min), texture));
            contentQuads.add(createQuad(v(min, maxY, max), v(min, minY, max), v(max, minY, max), v(max, maxY, max), texture));

            retQuads.addAll(contentQuads);
        }

        return retQuads;
    }

    private BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite) {
        Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

        BakedQuadBuilder builder = new BakedQuadBuilder(sprite);
        builder.setQuadOrientation(Direction.getFacingFromVector(normal.x, normal.y, normal.z));
        putVertex(builder, normal, v1.x, v1.y, v1.z, 2, 2, sprite);
        putVertex(builder, normal, v2.x, v2.y, v2.z, 2, 14, sprite);
        putVertex(builder, normal, v3.x, v3.y, v3.z, 14, 14, sprite);
        putVertex(builder, normal, v4.x, v4.y, v4.z, 14, 2, sprite);
        return builder.build();
    }

    private void putVertex(BakedQuadBuilder builder, Vec3d normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite) {
        ImmutableList<VertexFormatElement> elements = builder.getVertexFormat().getElements().asList();
        for (int j = 0 ; j < elements.size() ; j++) {
            VertexFormatElement e = elements.get(j);
            switch (e.getUsage()) {
                case POSITION:
                    builder.put(j, (float) x, (float) y, (float) z, 1.0f);
                    break;
                case COLOR:
                    builder.put(j, 1.0f, 1.0f, 1.0f, 1.0f);
                    break;
                case UV:
                    switch (e.getIndex()) {
                        case 0:
                            float iu = sprite.getInterpolatedU(u);
                            float iv = sprite.getInterpolatedV(v);
                            builder.put(j, iu, iv);
                            break;
                        case 2:
                            builder.put(j, 0.0f, 0.0f);
                            break;
                        default:
                            builder.put(j);
                            break;
                    }
                    break;
                case NORMAL:
                    builder.put(j, (float) normal.x, (float) normal.y, (float) normal.z);
                    break;
                default:
                    builder.put(j);
                    break;
            }
        }
    }

    private IBakedModel getContentModel(BlockState state) {
        if (state != null) {
            BlockRendererDispatcher dispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            BlockModelShapes shapes = dispatcher.getBlockModelShapes();
            return shapes.getModel(state);
        } else {
            return this.originalModel;
        }
    }

    public static final class CrucibleModelLoader implements IModelLoader<CrucibleModelGeometry> {
        @Override
        public void onResourceManagerReload(IResourceManager resourceManager) {
        }

        @Override
        public CrucibleModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
            return new CrucibleModelGeometry(ModelLoaderRegistry.VanillaProxy.Loader.INSTANCE.read(deserializationContext, modelContents));
        }
    }

    public static final class CrucibleModelGeometry implements ISimpleModelGeometry<CrucibleModelGeometry> {
        private final ModelLoaderRegistry.VanillaProxy model;

        public CrucibleModelGeometry(ModelLoaderRegistry.VanillaProxy model) {
            this.model = model;
        }

        @Override
        public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
            return new CrucibleBakedModel(model.bake(owner, bakery, spriteGetter, modelTransform, overrides, modelLocation));
        }

        @Override
        public void addQuads(IModelConfiguration owner, IModelBuilder<?> modelBuilder, ModelBakery bakery, Function<Material, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ResourceLocation modelLocation) {
            model.addQuads(owner, modelBuilder, bakery, spriteGetter, modelTransform, modelLocation);
        }


        @Override
        public Collection<Material> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            return model.getTextures(owner, modelGetter, missingTextureErrors);
        }
    }
}
