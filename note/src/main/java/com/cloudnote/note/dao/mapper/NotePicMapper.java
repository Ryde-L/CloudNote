package com.cloudnote.note.dao.mapper;

import com.cloudnote.note.pojo.NotePic;
import org.springframework.stereotype.Repository;


@Repository
public interface NotePicMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(NotePic record);

    NotePic selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(NotePic record);
}