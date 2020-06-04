package com.cloudnote.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElasticSearchDto {
    String took;
    boolean timed_out;
    Map<String ,Integer> _shards;
    Map<String,Object> hits;


}
