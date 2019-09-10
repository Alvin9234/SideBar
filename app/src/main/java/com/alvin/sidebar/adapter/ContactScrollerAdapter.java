package com.alvin.sidebar.adapter;

import com.alvin.sidebar.model.FriendEntity;
import com.alvin.sidebar.util.CharacterParserUtil;
import com.alvin.sidebar.util.Section;
import com.alvin.sidebar.util.SectionScrollAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContactScrollerAdapter implements SectionScrollAdapter {

    private List<Section> mSections;

    public ContactScrollerAdapter(List<FriendEntity> contacts) {
        initWithContacts(contacts);
    }

    @Override
    public int getSectionCount() {
        return mSections.size();
    }

    @Override
    public String getSectionTitle(int position) {
        return mSections.get(position).getTitle();
    }

    @Override
    public int getSectionWeight(int position) {
        return mSections.get(position).getWeight();
    }

    public Section fromSectionIndex( int sectionIndex) {
        return mSections.get(sectionIndex);
    }

    public List<String> getSectionsTitles() {
        if(mSections!=null){
            int size = mSections.size();
            List<String> sectionStrs = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                sectionStrs.add(mSections.get(i).getTitle());
            }
            return sectionStrs;
        }
        return new ArrayList<>();
    }

    public Section fromItemIndex(int itemIndex) {
        for (Section s : mSections) {
            final int range = s.getIndex() + s.getWeight();
            if (itemIndex < range) {
                return s;
            }
        }
        return mSections.get(mSections.size() - 1);
    }

    public int positionFromSection(int sectionIndex) {
        return mSections.get(sectionIndex).getIndex();
    }

    private void initWithContacts(List<FriendEntity> contacts) {
        mSections = new ArrayList<>();
        Collections.sort(contacts, FriendEntity.COMPARATOR);
        String sectionTitle = null;
        FriendEntity contact;
        int itemCount = 0;
        int size = contacts.size();
        for (int i = 0; i < size; i++) {
            contact = contacts.get(i);
            String key1 = CharacterParserUtil.getSelling(contact.getUserName());
            String firstLetter = CharacterParserUtil.getSortLetterBySortKey(key1);

            if (sectionTitle == null) {
                sectionTitle = firstLetter;
            }
            if (sectionTitle.equals(firstLetter)) {
                itemCount++;
                continue;
            }

            mSections.add(new Section(i - itemCount, sectionTitle, itemCount));
            sectionTitle = firstLetter;
            itemCount = 1;
        }
        //上面的循环结果，最后一类不同拼音的无法添加进去，所以循环结束后，把最后一个类型添加进去
        mSections.add(new Section(size - itemCount, sectionTitle, itemCount));
        //添加特殊符号到第一个
        mSections.add(0,new Section(-1, "#", -1));
    }
}
