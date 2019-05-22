package com.cgy.hupu.module.imagepreview;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cgy.hupu.Logger;
import com.cgy.hupu.R;
import com.cgy.hupu.module.BaseFragment;
import com.cgy.hupu.utils.ResourceUtil;
import com.cgy.hupu.widget.ImageLoadProgressBar;
import com.cgy.hupu.widget.photodraweeview.OnViewTapListener;
import com.cgy.hupu.widget.photodraweeview.PhotoDraweeView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * @author cgy
 * @desctiption
 * @date 2019/5/22 14:37
 */
public class ImageFragment extends BaseFragment {

    public static ImageFragment newInstance(String url) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.image)
    PhotoDraweeView image;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.progress)
    SmoothProgressBar progress;
    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    Unbinder unbinder;

    private String url;

    @Override
    public void initInjector() {

    }

    @Override
    public int initContentView() {
        return R.layout.fragment_image;
    }

    @Override
    public void getBundle(Bundle bundle) {
        url = bundle.getString("url");
    }

    @Override
    public void initUI(View view) {
        unbinder = ButterKnife.bind(this, view);
        progress.setIndeterminate(true);
        image.setOnViewTapListener((view1, x, y) -> getActivity().finish());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            rlProgress.setPadding(0, ResourceUtil.getStatusBarHeight(getActivity()), 0, 0);
        }
    }

    @Override
    public void initData() {
        showContent(true);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();

        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources()).setProgressBarImage(
                new ImageLoadProgressBar(level -> {
                    if (level > 100 && progress.getVisibility() == View.VISIBLE) {
                        progress.setVisibility(View.GONE);
                    }
                }, ResourceUtil.getThemeColor(getActivity()))).build();
        hierarchy.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);

        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setControllerListener(listener);
        controller.setImageRequest(request);
        controller.setOldController(image.getController());
        controller.setAutoPlayAnimations(true);
        image.setHierarchy(hierarchy);
        image.setController(controller.build());
    }

    private BaseControllerListener<ImageInfo> listener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
            Logger.e("onFailure:" + throwable.getMessage());
            progress.setVisibility(View.GONE);
            tvInfo.setVisibility(View.VISIBLE);
            tvInfo.setText("图片加载失败");
        }

        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            Logger.d("onFinalImageSet");
            if (imageInfo == null) {
                return;
            }
            image.update(imageInfo.getWidth(), imageInfo.getHeight());
            progress.setVisibility(View.GONE);
        }

        @Override
        public void onSubmit(String id, Object callerContext) {
            super.onSubmit(id, callerContext);
            Logger.d("onSubmit");
            progress.setVisibility(View.VISIBLE);
            tvInfo.setVisibility(View.GONE);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
