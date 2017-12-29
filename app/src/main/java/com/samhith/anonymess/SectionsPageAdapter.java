package com.samhith.anonymess;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sam on 12/3/17.
 */


class SectionsPageAdapter extends FragmentPagerAdapter{

    public static final int CONVERSATIONS_FRAGMENT = 0;
    public static final int USER_CHATS_FRAGMENT = 1;
    public static final int SAVED_CHATS_FRAGMENT = 2;

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case CONVERSATIONS_FRAGMENT:
                ConversationFragment conversationFragment = new ConversationFragment();
                return conversationFragment;

            case USER_CHATS_FRAGMENT:
                UserChatsFragment userChatsFragment = new UserChatsFragment();
                return userChatsFragment;

            case SAVED_CHATS_FRAGMENT:
                SavedChatsFragment savedChatsFragment = new SavedChatsFragment();
                return savedChatsFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case CONVERSATIONS_FRAGMENT:
                return "Conversations";

            case USER_CHATS_FRAGMENT:
                return "My Chats";

            case SAVED_CHATS_FRAGMENT:
                return "Saved";

            default:
                return null;
        }
    }
}
