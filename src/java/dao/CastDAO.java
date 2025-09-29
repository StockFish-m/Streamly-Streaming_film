package dao;

import java.util.List;
import model.content.Cast;

public interface CastDAO {
    List<Cast> getAllCasts();
    Cast getCastById(String id);
    void addCast(Cast cast);
    boolean updateCast(Cast cast);
    boolean deleteCast(String id);
}
