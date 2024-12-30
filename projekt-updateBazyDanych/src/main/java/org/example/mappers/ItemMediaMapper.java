package org.example.mappers;

import org.example.DTOs.ItemMediaDto;
import org.example.model.ItemMedia;

public class ItemMediaMapper implements IMapEntities<ItemMediaDto, ItemMedia> {
    @Override
    public ItemMedia map(ItemMediaDto itemMediaDto) {
        return map(itemMediaDto, new ItemMedia());
    }

    @Override
    public ItemMedia map(ItemMediaDto itemMediaDto, ItemMedia itemMedia) {
        itemMedia.setIconUrl(itemMediaDto.getMediaUrl());
        return itemMedia;
    }
}
