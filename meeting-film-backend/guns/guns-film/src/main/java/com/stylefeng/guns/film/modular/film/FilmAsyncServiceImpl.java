package com.stylefeng.guns.film.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.film.ActorVO;
import com.stylefeng.guns.api.film.FilmAsyncService;
import com.stylefeng.guns.api.film.FilmDescVO;
import com.stylefeng.guns.api.film.ImgVO;
import com.stylefeng.guns.film.persistence.dao.ActorMapper;
import com.stylefeng.guns.film.persistence.dao.FilmInfoMapper;
import com.stylefeng.guns.film.persistence.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service
public class FilmAsyncServiceImpl implements FilmAsyncService {
    @Autowired
    private FilmInfoMapper filmInfoMapper;

    @Autowired
    private ActorMapper actorMapper;

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        com.stylefeng.guns.film.persistence.model.FilmInfo filmInfo = getFilmInfo(filmId);
        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setFilmId(filmId);
        filmDescVO.setBiography(filmInfo.getBiography());

        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        com.stylefeng.guns.film.persistence.model.FilmInfo filmInfo = getFilmInfo(filmId);
        String filmImgStr = filmInfo.getFilmImgs();
        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO();
        imgVO.setMainImg(filmImgs[0]);
        imgVO.setImg01(filmImgs[1]);
        imgVO.setImg02(filmImgs[2]);
        imgVO.setImg03(filmImgs[3]);
        imgVO.setImg04(filmImgs[4]);

        return imgVO;
    }

    @Override
    public ActorVO getDirectorInfo(String filmId) {
        com.stylefeng.guns.film.persistence.model.FilmInfo filmInfo = getFilmInfo(filmId);
        Actor director = actorMapper.selectById(filmInfo.getDirectorId());

        ActorVO actorVO = new ActorVO();
        actorVO.setImgAddress(director.getActorImg());
        actorVO.setDirectorName(director.getActorName());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        return actorMapper.getActors(filmId);
    }

    private com.stylefeng.guns.film.persistence.model.FilmInfo getFilmInfo(String filmId) {
        com.stylefeng.guns.film.persistence.model.FilmInfo filmInfo = new com.stylefeng.guns.film.persistence.model.FilmInfo();
        filmInfo.setFilmId(filmId);

        return filmInfoMapper.selectOne(filmInfo);
    }
}
