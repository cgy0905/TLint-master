// Generated code from Butter Knife. Do not modify!
package com.gzsll.hupu.ui.imagepreview;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.gzsll.hupu.R;
import com.gzsll.hupu.widget.photodraweeview.PhotoDraweeView;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageFragment_ViewBinding<T extends ImageFragment> implements Unbinder {
  protected T target;

  @UiThread
  public ImageFragment_ViewBinding(T target, View source) {
    this.target = target;

    target.image = Utils.findRequiredViewAsType(source, R.id.image, "field 'image'", PhotoDraweeView.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progress'", SmoothProgressBar.class);
    target.rlProgress = Utils.findRequiredViewAsType(source, R.id.rlProgress, "field 'rlProgress'", RelativeLayout.class);
    target.tvInfo = Utils.findRequiredViewAsType(source, R.id.tvInfo, "field 'tvInfo'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.image = null;
    target.progress = null;
    target.rlProgress = null;
    target.tvInfo = null;

    this.target = null;
  }
}
