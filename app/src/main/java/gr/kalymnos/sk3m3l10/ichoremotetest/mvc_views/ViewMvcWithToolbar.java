package gr.kalymnos.sk3m3l10.ichoremotetest.mvc_views;

import android.support.v7.widget.Toolbar;

public interface ViewMvcWithToolbar extends ViewMvc {
    Toolbar getToolbar();

    void setToolbarTitle(String title);

    void setToolbarSubtitle(String subtitle);
}
