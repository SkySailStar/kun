package cn.kun.system.file.controller;

import cn.kun.base.core.global.controller.BaseController;
import cn.kun.base.core.global.entity.BaseResult;
import cn.kun.base.core.global.entity.dto.BaseIdListDTO;
import cn.kun.base.api.entity.system.dto.FileEditDTO;
import cn.kun.base.api.entity.system.po.File;
import cn.kun.base.api.entity.system.vo.FileInfoVO;
import cn.kun.system.file.entity.vo.FileVO;
import cn.kun.system.file.entity.vo.UploadVO;
import cn.kun.system.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    public BaseResult<UploadVO> upload(MultipartFile file) throws Exception {
        return success(fileService.upload(file));
    }
    
    @Operation(summary = "修改")
    @PutMapping
    public BaseResult<File> edit(@RequestBody FileEditDTO dto) {
        return success(fileService.edit(dto));
    }

    @Operation(summary = "通过ID预览")
    @GetMapping("preview/id/{id}")
    public BaseResult<String> previewById(@PathVariable Long id) throws Exception {
        return success(fileService.previewById(id));
    }
    
    @Operation(summary = "通过名称预览")
    @GetMapping("preview/name/{name}")
    public BaseResult<String> previewByName(@PathVariable String name) throws Exception {
        return success(fileService.previewByName(name));
    }

    @Operation(summary = "通过ID下载")
    @PostMapping("download/id/{id}")
    public BaseResult<Void> downloadById(@PathVariable Long id, HttpServletResponse res) throws Exception {
        fileService.downloadById(id, res);
        return success();
    }
    
    @Operation(summary = "通过名称下载")
    @PostMapping("download/name/{name}")
    public BaseResult<Void> downloadByName(@PathVariable String name, HttpServletResponse res) throws Exception {
        fileService.downloadByName(name, res);
        return success();
    }

    @Operation(summary = "查看所有文件")
    @GetMapping("list_all")
    public BaseResult<List<FileVO>> listAll() throws Exception {
        return success(fileService.listObjects());
    }

    @Operation(summary = "通过ID删除")
    @DeleteMapping("id/{id}")
    public BaseResult<Boolean> del(@PathVariable Long id) throws Exception {
        return success(fileService.delFileById(id));
    }
    
    @Operation(summary = "通过名称删除")
    @DeleteMapping("name/{name}")
    public BaseResult<Boolean> del(@PathVariable String name) throws Exception {
        return success(fileService.delFileByName(name));
    }
    
    @Operation(summary = "使文件生效")
    @PutMapping("enable/{id}")
    public BaseResult<Boolean> enable(@PathVariable Long id) {
        return success(fileService.enable(id));
    }
    
    @Operation(summary = "通过ID获取路径")
    @GetMapping("path/{id}")
    public BaseResult<String> getPathById(@PathVariable Long id) {
        return success(fileService.getPathById(id));
    }

    @Operation(summary = "通过ID获取信息")
    @GetMapping("info/{id}")
    public BaseResult<FileInfoVO> getInfoById(@PathVariable Long id) {
        return success(fileService.getInfoById(id));
    }
    
    @Operation(summary = "通过ID列表获取信息列表")
    @PostMapping("infos")
    public BaseResult<List<FileInfoVO>> getInfoList(@RequestBody @Valid BaseIdListDTO dto) {
        return success(fileService.getInfoList(dto));
    }
    
}
