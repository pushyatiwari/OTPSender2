package coml.example.android.otpsender.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import coml.example.android.otpsender.Fragments.EditReceivers;
import coml.example.android.otpsender.Fragments.History;
import coml.example.android.otpsender.Fragments.RegisterNew;

public class sectionsPagerAdapter extends FragmentPagerAdapter {

    public sectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new RegisterNew();

            case 1:
                return new EditReceivers();
            case 2:
                return new History();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Register New";
            case 1:
                return "Edit Receivers";
            case 2:
                return "Edit History";
        }
        return null;
    }
}
