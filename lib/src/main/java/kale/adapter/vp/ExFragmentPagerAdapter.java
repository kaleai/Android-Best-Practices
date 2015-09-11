package kale.adapter.vp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * @author Jack Tony
 * @date 2015/9/8
 */
public abstract class ExFragmentPagerAdapter extends FragmentPagerAdapter {

    private FragmentManager mFm;
    private SparseArray<String> mFragmentTagSArr;
    private Fragment mCurrentFragment;

    public ExFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFm = fm;
        mFragmentTagSArr = new SparseArray<>();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mCurrentFragment = (Fragment) object;
        super.setPrimaryItem(container, position, object);
        position = position % getCount();
        if (mFragmentTagSArr.valueAt(position) == null) {
            mFragmentTagSArr.put(position, mCurrentFragment.getTag());
        }
    }

    @Nullable
    public <E extends Fragment> E getFragment(int position) {
        position = position % getCount();
        String tag = mFragmentTagSArr.get(position);
        if (tag != null) {
            return (E) mFm.findFragmentByTag(tag);
        } else {
            return null;
        }
    }

    @Nullable
    public <E extends Fragment> E getFragment(Class<E> cls) {
        for (int i = 0; i < mFragmentTagSArr.size(); i++) {
            if (getFragment(i).getClass().getName().equals(cls.getName())) {
                return getFragment(i);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <E extends Fragment> E getCurrentFragment() {
        return (E) mCurrentFragment;
    }

}
