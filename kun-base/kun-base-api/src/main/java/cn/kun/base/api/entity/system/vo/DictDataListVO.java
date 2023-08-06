package cn.kun.base.api.entity.system.vo;

import cn.kun.base.core.global.entity.vo.BaseSelectVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 字典数据-列表-返回值
 *
 * @author SkySailStar
 * @date 2023-04-04 16:42
 */
@Schema(description = "字典数据-列表-返回值")
@Data
public class DictDataListVO implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典数据列表")
    private List<BaseSelectVO> dictDataList;
}
