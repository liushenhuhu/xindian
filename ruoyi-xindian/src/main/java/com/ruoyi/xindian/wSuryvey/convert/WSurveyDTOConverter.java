package com.ruoyi.xindian.wSuryvey.convert;


import com.ruoyi.xindian.wSuryvey.domain.WSurvey;
import com.ruoyi.xindian.wSuryvey.domain.WSurveyDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WSurveyDTOConverter {

    WSurveyDTOConverter INSTANCE = Mappers.getMapper(WSurveyDTOConverter.class);

    WSurveyDTO covertEntertyTODTO(WSurvey wSurvey);

}
