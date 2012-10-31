package com.laaficionmanda.android.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

/**
 * An abstract class that handles some common action bar-related functionality
 * in the app. This class provides functionality useful for both phones and
 * tablets, and does not require any Android 3.0-specific features, although it
 * uses them if available.
 * 
 * Two implementations of this class are {@link ActionBarHelperBase} for a
 * pre-Honeycomb version of the action bar, and {@link ActionBarHelperHoneycomb}
 * , which uses the built-in ActionBar features in Android 3.0 and later.
 */
public abstract class ActionBarHelper {
    protected Activity mActivity;

    /**
     * Indicate if the setup action bar process must render the back button.
     */
    protected boolean showBackButton = true;

    /**
     * Indicate if the setup action bar process render the user goal progress bar.
     */
    protected boolean showProgressBar = false;
    

    /**
     * Gets a boolean flag indicating if the  back button is showed.
     */
    public boolean isShowBackButton() {
        return showBackButton;
    }

    /**
     * Sets the value of <code>showBackButton</code> flag.
     * @param showBackButton
     */
    public void setShowBackButton(boolean showBackButton) {
        this.showBackButton = showBackButton;
    }

    /**
     * Gets a boolean flag indicating if the progress bar is showed.
     *
     */
    public boolean isShowProgressBar() {
        return showProgressBar;
    }

    /**
     * Sets the value of <code>showProgessBar</code> flag.
     * @param showProgressBar
     */
    public void setShowProgressBar(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    /**
     * Factory method for creating {@link ActionBarHelper} objects for a given
     * activity. Depending on which device the app is running, either a basic
     * helper or Honeycomb-specific helper will be returned.
     */
    public static ActionBarHelper createInstance(Activity activity) {

        // TODO Descomentar cuando el project target sea superior a android-14
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        // {
        // return new ActionBarHelperICS(activity);
        // } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        // return new ActionBarHelperHoneycomb(activity);
        // } else {
        // return new ActionBarHelperBase(activity);
        // }

        // Solamente se utiliza el básico para versiones pre-API11
        return new ActionBarHelperBase(activity);
    }

    protected ActionBarHelper(Activity activity) {
        mActivity = activity;
    }

    /**
     * Action bar helper code to be run in
     * {@link Activity#onCreate(android.os.Bundle)}.
     */
    public abstract void onCreate(Bundle savedInstanceState);

    /**
     * Action bar helper code to be run in
     * {@link Activity#onPostCreate(android.os.Bundle)}.
     */
    public abstract void onPostCreate(Bundle savedInstanceState);

    /**
     * Action bar helper code to be run in
     * {@link Activity#onCreateOptionsMenu(android.view.Menu)}.
     * 
     * NOTE: Setting the visibility of menu items in <em>menu</em> is not
     * currently supported.
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /**
     * Action bar helper code to be run in
     * {@link Activity#onTitleChanged(CharSequence, int)}.
     */
    protected abstract void onTitleChanged(CharSequence title, int color);

    /**
     * Sets the indeterminate loading state of the item with ID
     * {@link R.id.menu_refresh}. (where the item ID was menu_refresh).
     */
    public abstract void setRefreshActionItemState(boolean refreshing);

    /**
     * Returns a {@link MenuInflater} for use when inflating menus. The
     * implementation of this method in {@link ActionBarHelperBase} returns a
     * wrapped menu inflater that can read action bar metadata from a menu
     * resource pre-Honeycomb.
     */
    public MenuInflater getMenuInflater(MenuInflater superMenuInflater) {
        return superMenuInflater;
    }

    /**
     * Sets the action bar item visibility.
     * @param item
     * ID of the item to change. i.e. R.id.item_id
     * @param visibility
     * Visibility to set on the actiobar item. i.e. View.GONE or View.VISIBLE.
     */
    public abstract void setItemVisibility(int item, int visibility);
}
