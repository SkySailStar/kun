package cn.kun.demo.file.controller;

import cn.kun.demo.file.entity.vo.ItemVO;
import cn.kun.demo.file.service.FileService;
import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件-控制层
 *
 * @author lujun
 */
@Tag(name = "文件")
@RestController
@RequestMapping("file")
public class FileController extends BaseController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "上传")
    @PostMapping("upload")
    public BaseResult<String> upload(MultipartFile file) throws Exception {

        return success(fileService.upload(file));
    }

    @Operation(summary = "预览")
    @GetMapping("preview/{name}")
    public BaseResult<String> preview(@PathVariable String name) throws Exception {
        
        return success(fileService.preview(name));
    }

    @Operation(summary = "下载")
    @PostMapping("download/{name}")
    public BaseResult<Void> download(@PathVariable String name, HttpServletResponse res) throws Exception {
        
        fileService.download(name, res);
        return success();
    }

    @Operation(summary = "查看桶里所有文件")
    @GetMapping("list_all")
    public BaseResult<List<ItemVO>> listAll() throws Exception {
        
        return success(fileService.listObjects());
    }

    @Operation(summary = "删除")
    @DeleteMapping("{name}")
    public BaseResult<Boolean> del(@PathVariable String name) throws Exception {

        fileService.delFile(name);
        return success();
    }

}
