package com.stylefeng.guns.gateway.modular.cinema;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.*;
import com.stylefeng.guns.gateway.modular.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    private static final String IMG_PRE = "http://img.meetingfilm.cn";

    @Reference(interfaceClass = CinemaService.class, check = false)
    private CinemaService cinemaService;

    /**
     * 查询影院列表
     *
     * @param cinemaQueryVO
     * @return
     */
    @GetMapping("/getCinemas")
    public ResponseVO getCinemas(CinemaQueryVO cinemaQueryVO) {
        try {
            Page<CinemaVO> cinemaVOPage = cinemaService.getCinemas(cinemaQueryVO);
            if (CollectionUtils.isEmpty(cinemaVOPage.getRecords())) {
                return ResponseVO.success("影院列表为空");
            } else {
                CinemaResponseVO cinemaResponseVO = new CinemaResponseVO();
                cinemaResponseVO.setCinemas(cinemaVOPage.getRecords());

                return ResponseVO.success("", cinemaResponseVO,
                        cinemaVOPage.getCurrent(), (int) cinemaVOPage.getPages());
            }
        } catch (Exception e) {
            log.error("获取影院列表异常", e);
            return ResponseVO.serviceFail("获取影院列表失败");
        }
    }

    /**
     * 获取影院列表查询条件
     *
     * @param cinemaQueryVO
     * @return
     */
    @GetMapping("/getCondition")
    public ResponseVO getCondition(CinemaQueryVO cinemaQueryVO) {
        try {
            List<BrandVO> brands = cinemaService.getBrands(cinemaQueryVO.getBrandId());
            List<AreaVO> areas = cinemaService.getAreas(cinemaQueryVO.getDistrictId());
            List<HallTypeVO> hallTypes = cinemaService.getHallTypes(cinemaQueryVO.getHallType());

            CinemaConditionResponseVO cinemaConditionResponseVO = new CinemaConditionResponseVO();
            cinemaConditionResponseVO.setBrandList(brands);
            cinemaConditionResponseVO.setAreasList(areas);
            cinemaConditionResponseVO.setHallTypeList(hallTypes);

            return ResponseVO.success(null, cinemaConditionResponseVO);
        } catch (Exception e) {
            log.info("获取影院条件列表失败", e);
            return ResponseVO.serviceFail("获取影院条件列表失败");
        }
    }

    /**
     * 获取播放场次
     *
     * @param cinemaId
     * @return
     */
    @GetMapping("/getFields")
    public ResponseVO getFields(@RequestParam Integer cinemaId) {
        try {
            CinemaInfoVO cinemaInfoVO = cinemaService.getCinemaInfoById(cinemaId);
            List<FilmInfoVO> filmInfoVOs = cinemaService.getFilmInfoByCinemaId(cinemaId);

            CinemaFieldsResponseVO cinemaFieldsResponseVO = new CinemaFieldsResponseVO();
            cinemaFieldsResponseVO.setCinemaInfo(cinemaInfoVO);
            cinemaFieldsResponseVO.setFilmList(filmInfoVOs);

            return ResponseVO.success(IMG_PRE, cinemaFieldsResponseVO);
        } catch (Exception e) {
            log.info("获取场次列表失败", e);
            return ResponseVO.serviceFail("获取场次列表失败");
        }
    }

    /**
     * 获取场次详细信息
     *
     * @param cinemaId
     * @param fieldId
     * @return
     */
    @PostMapping("/getFieldInfo")
    public ResponseVO getFieldInfo(Integer cinemaId, Integer fieldId) {
        try {
            CinemaInfoVO cinemaInfoVO = cinemaService.getCinemaInfoById(cinemaId);
            FilmInfoVO filmInfoVO = cinemaService.getFilmInfoByFieldId(fieldId);
            HallInfoVO hallInfoVO = cinemaService.getFilmFieldInfo(fieldId);

            CinemaFieldResponseVO cinemaFieldResponseVO = new CinemaFieldResponseVO();
            cinemaFieldResponseVO.setCinemaInfo(cinemaInfoVO);
            cinemaFieldResponseVO.setFilmInfo(filmInfoVO);
            cinemaFieldResponseVO.setHallInfo(hallInfoVO);

            return ResponseVO.success(IMG_PRE, cinemaFieldResponseVO);
        } catch (Exception e) {
            log.info("获取场次详情失败", e);
            return ResponseVO.serviceFail("获取场次详情失败");
        }
    }
}
