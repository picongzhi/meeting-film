package com.stylefeng.guns.gateway.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.api.film.*;
import com.stylefeng.guns.gateway.modular.vo.FilmConditionVO;
import com.stylefeng.guns.gateway.modular.vo.FilmIndexVO;
import com.stylefeng.guns.gateway.modular.vo.FilmRequestVO;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/film")
public class FilmController {
    private static final String IMG_PRE = "http://img.meetingfilm.cn";

    @Reference(interfaceClass = FilmService.class, check = false)
    private FilmService filmService;

    @Reference(interfaceClass = FilmAsyncService.class, async = true, check = false)
    private FilmAsyncService filmAsyncService;

    @GetMapping("/getIndex")
    public ResponseVO getIndex() {
        FilmIndexVO filmIndexVO = new FilmIndexVO();
        // 获取banner信息
        filmIndexVO.setBanners(filmService.getBanners());
        // 获取正在热映的电影
        filmIndexVO.setHotFilms(filmService.getHotFilms(true, 8, 1, 1, 99, 99, 99));
        // 获取即将上映的电影
        filmIndexVO.setSoonFilms(filmService.getSoonFilms(true, 8, 1, 1, 99, 99, 99));
        // 获取票房排行榜
        filmIndexVO.setBoxRanking(filmService.getBoxRanking());
        // 获取受欢迎的榜单
        filmIndexVO.setExpectRanking(filmService.getExpectRanking());
        // 获取Top 100
        filmIndexVO.setTop100(filmService.getTop());

        return ResponseVO.success(IMG_PRE, filmIndexVO);
    }

    @GetMapping("/getConditionList")
    public ResponseVO getConditionList(@RequestParam(name = "catId", required = false, defaultValue = "99") String catId,
                                       @RequestParam(name = "sourceId", required = false, defaultValue = "99") String sourceId,
                                       @RequestParam(name = "yearId", required = false, defaultValue = "99") String yearId) {
        FilmConditionVO filmConditionVO = new FilmConditionVO();
        // 获取类型集合
        List<CatVO> catVOList = filmService.getCats();
        for (CatVO catVO : catVOList) {
            if (catVO.getCatId().equals(catId)) {
                catVO.setActive(true);
                break;
            }
        }
        filmConditionVO.setCatInfo(catVOList);

        // 获取片源集合
        List<SourceVO> sourceVOList = filmService.getSources();
        for (SourceVO sourceVO : sourceVOList) {
            if (sourceVO.getSourceId().equals(sourceId)) {
                sourceVO.setActive(true);
                break;
            }
        }
        filmConditionVO.setSourceInfo(sourceVOList);

        // 获取年代集合
        List<YearVO> yearVOList = filmService.getYears();
        for (YearVO yearVO : yearVOList) {
            if (yearVO.getYearId().equals(yearId)) {
                yearVO.setActive(true);
                break;
            }
        }
        filmConditionVO.setYearInfo(yearVOList);

        return ResponseVO.success(filmConditionVO);
    }

    @GetMapping("/getFilms")
    public ResponseVO getFilms(FilmRequestVO requestVO) {
        FilmVO filmVO = null;
        switch (requestVO.getShowType()) {
            case 1:
                filmVO = filmService.getHotFilms(false, requestVO.getPageSize(), requestVO.getNowPage(),
                        requestVO.getSortId(), requestVO.getSourceId(), requestVO.getCatId(), requestVO.getYearId());
                break;
            case 2:
                filmVO = filmService.getSoonFilms(false, requestVO.getPageSize(), requestVO.getNowPage(),
                        requestVO.getSortId(), requestVO.getSourceId(), requestVO.getCatId(), requestVO.getYearId());
                break;
            case 3:
                filmVO = filmService.getClassicFilms(requestVO.getPageSize(), requestVO.getNowPage(),
                        requestVO.getSortId(), requestVO.getSourceId(), requestVO.getCatId(), requestVO.getYearId());
                break;
            default:
                filmVO = filmService.getHotFilms(false, requestVO.getPageSize(), requestVO.getNowPage(),
                        requestVO.getSortId(), requestVO.getSourceId(), requestVO.getCatId(), requestVO.getYearId());
                break;
        }

        return ResponseVO.success(IMG_PRE, filmVO.getFilmInfos(),
                requestVO.getNowPage(), filmVO.getTotalPage());
    }

    @GetMapping("/films/{idOrName}")
    public ResponseVO films(@PathVariable String idOrName,
                            @RequestParam(required = false, defaultValue = "1") Integer searchType)
            throws ExecutionException, InterruptedException {
        FilmDetailVO filmDetailVO = filmService.getFilmDetail(searchType, idOrName);
        // 获取影片描述信息
        filmAsyncService.getFilmDesc(filmDetailVO.getFilmId());
        Future<FilmDescVO> filmDescVOFuture = RpcContext.getContext().getFuture();

        // 获取图片信息
        filmAsyncService.getImgs(filmDetailVO.getFilmId());
        Future<ImgVO> imgVOFuture = RpcContext.getContext().getFuture();

        // 获取演员信息
        filmAsyncService.getDirectorInfo(filmDetailVO.getFilmId());
        Future<ActorVO> directorVOFuture = RpcContext.getContext().getFuture();

        filmAsyncService.getActors(filmDetailVO.getFilmId());
        Future<List<ActorVO>> actorsFuture = RpcContext.getContext().getFuture();

        InfoResponseVO infoResponseVO = new InfoResponseVO();
        infoResponseVO.setFilmId(filmDetailVO.getFilmId());
        infoResponseVO.setBiography(filmDescVOFuture.get().getBiography());
        infoResponseVO.setImgs(imgVOFuture.get());

        ActorResponseVO actorResponseVO = new ActorResponseVO();
        actorResponseVO.setDirector(directorVOFuture.get());
        actorResponseVO.setActors(actorsFuture.get());
        infoResponseVO.setActors(actorResponseVO);

        filmDetailVO.setInfo4(infoResponseVO);

        return ResponseVO.success(IMG_PRE, filmDetailVO);
    }
}
