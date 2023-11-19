package andrehsvictor.fitzone.api.v1.util;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

public class FitzoneMapper {
    private static ModelMapper mapper = new ModelMapper();

    public static <O,D> D convert(O originObject, Class<D> destinationClass) {
        return mapper.map(originObject, destinationClass);
    }

    public static <O,D> List<D> convertList(List<O> originList, Class<D> destinationClass) {
        List<D> destinationList = new ArrayList<>();
        for(O o : originList)
            destinationList.add(mapper.map(o, destinationClass));
        return destinationList;
    }
}
