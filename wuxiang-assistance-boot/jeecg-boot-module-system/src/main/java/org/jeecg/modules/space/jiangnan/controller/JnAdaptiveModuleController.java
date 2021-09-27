package org.jeecg.modules.space.jiangnan.controller;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.space.jiangnan.entity.JnAdaptiveModule;
import org.jeecg.modules.space.jiangnan.service.IJnAdaptiveModuleService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 江南大屏模块
 * @Author: jeecg-boot
 * @Date: 2021-08-25
 * @Version: V1.0
 */
@Api(tags = "江南大屏模块")
@RestController
@RequestMapping("/cockpit/jiangnan/jnAdaptiveModule")
@Slf4j
public class JnAdaptiveModuleController extends JeecgController<JnAdaptiveModule, IJnAdaptiveModuleService> {
    @Autowired
    private IJnAdaptiveModuleService jnAdaptiveModuleService;

    /**
     * 分页列表查询
     *
     * @param jnAdaptiveModule
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "江南大屏模块-分页列表查询")
    @ApiOperation(value = "江南大屏模块-分页列表查询", notes = "江南大屏模块-分页列表查询")
    @GetMapping(value = "/rootList")
    public Result<?> queryPageList(JnAdaptiveModule jnAdaptiveModule,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        String hasQuery = req.getParameter("hasQuery");
        if (hasQuery != null && "true".equals(hasQuery)) {
            QueryWrapper<JnAdaptiveModule> queryWrapper = QueryGenerator.initQueryWrapper(jnAdaptiveModule, req.getParameterMap());
            List<JnAdaptiveModule> list = jnAdaptiveModuleService.queryTreeListNoPage(queryWrapper);
            IPage<JnAdaptiveModule> pageList = new Page<>(1, 10, list.size());
            pageList.setRecords(list);
            return Result.OK(pageList);
        } else {
            String parentId = jnAdaptiveModule.getPid();
            if (oConvertUtils.isEmpty(parentId)) {
                parentId = "0";
            }
            jnAdaptiveModule.setPid(null);
            QueryWrapper<JnAdaptiveModule> queryWrapper = QueryGenerator.initQueryWrapper(jnAdaptiveModule, req.getParameterMap());
            // 使用 eq 防止模糊查询
            queryWrapper.eq("pid", parentId);
            Page<JnAdaptiveModule> page = new Page<JnAdaptiveModule>(pageNo, pageSize);
            IPage<JnAdaptiveModule> pageList = jnAdaptiveModuleService.page(page, queryWrapper);
            return Result.OK(pageList);
        }
    }

    @AutoLog(value = "江南大屏模块-列表查询")
    @ApiOperation(value = "江南大屏模块-列表查询", notes = "江南大屏模块-列表查询")
    @GetMapping(value = "/rootListNoPage")
    public Result<?> queryList(JnAdaptiveModule jnAdaptiveModule,
                               HttpServletRequest req) {
        String hasQuery = req.getParameter("hasQuery");
        if (hasQuery != null && "true".equals(hasQuery)) {
            QueryWrapper<JnAdaptiveModule> queryWrapper = QueryGenerator.initQueryWrapper(jnAdaptiveModule, req.getParameterMap());
            List<JnAdaptiveModule> list = jnAdaptiveModuleService.queryTreeListNoPage(queryWrapper);
            return Result.OK(list);
        } else {
            String parentId = jnAdaptiveModule.getPid();
            if (oConvertUtils.isEmpty(parentId)) {
                parentId = "0";
            }
            jnAdaptiveModule.setPid(null);
            QueryWrapper<JnAdaptiveModule> queryWrapper = QueryGenerator.initQueryWrapper(jnAdaptiveModule, req.getParameterMap());
            // 使用 eq 防止模糊查询
            queryWrapper.eq("pid", parentId);
            List<JnAdaptiveModule> pageList = jnAdaptiveModuleService.list(queryWrapper);
            return Result.OK(pageList);
        }
    }

    /**
     * 获取子数据
     *
     * @param jnAdaptiveModule
     * @param req
     * @return
     */
    @AutoLog(value = "江南大屏模块-获取子数据")
    @ApiOperation(value = "江南大屏模块-获取子数据", notes = "江南大屏模块-获取子数据")
    @GetMapping(value = "/childList")
    public Result<?> queryPageList(JnAdaptiveModule jnAdaptiveModule, HttpServletRequest req) {
        QueryWrapper<JnAdaptiveModule> queryWrapper = QueryGenerator.initQueryWrapper(jnAdaptiveModule, req.getParameterMap());
        List<JnAdaptiveModule> list = jnAdaptiveModuleService.list(queryWrapper);
        IPage<JnAdaptiveModule> pageList = new Page<>(1, 10, list.size());
        pageList.setRecords(list);
        return Result.OK(pageList);
    }

    /**
     * 批量查询子节点
     *
     * @param parentIds 父ID（多个采用半角逗号分割）
     * @param parentIds
     * @return 返回 IPage
     * @return
     */
    @AutoLog(value = "江南大屏模块-批量获取子数据")
    @ApiOperation(value = "江南大屏模块-批量获取子数据", notes = "江南大屏模块-批量获取子数据")
    @GetMapping("/getChildListBatch")
    public Result getChildListBatch(@RequestParam("parentIds") String parentIds) {
        try {
            QueryWrapper<JnAdaptiveModule> queryWrapper = new QueryWrapper<>();
            List<String> parentIdList = Arrays.asList(parentIds.split(","));
            queryWrapper.in("pid", parentIdList);
            List<JnAdaptiveModule> list = jnAdaptiveModuleService.list(queryWrapper);
            IPage<JnAdaptiveModule> pageList = new Page<>(1, 10, list.size());
            pageList.setRecords(list);
            return Result.OK(pageList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("批量查询子节点失败：" + e.getMessage());
        }
    }

    /**
     * 添加
     *
     * @param jnAdaptiveModule
     * @return
     */
    @AutoLog(value = "江南大屏模块-添加")
    @ApiOperation(value = "江南大屏模块-添加", notes = "江南大屏模块-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody JnAdaptiveModule jnAdaptiveModule) {
        jnAdaptiveModuleService.addJnAdaptiveModule(jnAdaptiveModule);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param jnAdaptiveModule
     * @return
     */
    @AutoLog(value = "江南大屏模块-编辑")
    @ApiOperation(value = "江南大屏模块-编辑", notes = "江南大屏模块-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody JnAdaptiveModule jnAdaptiveModule) {
        jnAdaptiveModuleService.updateJnAdaptiveModule(jnAdaptiveModule);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "江南大屏模块-通过id删除")
    @ApiOperation(value = "江南大屏模块-通过id删除", notes = "江南大屏模块-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        jnAdaptiveModuleService.deleteJnAdaptiveModule(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "江南大屏模块-批量删除")
    @ApiOperation(value = "江南大屏模块-批量删除", notes = "江南大屏模块-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.jnAdaptiveModuleService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "江南大屏模块-通过id查询")
    @ApiOperation(value = "江南大屏模块-通过id查询", notes = "江南大屏模块-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        JnAdaptiveModule jnAdaptiveModule = jnAdaptiveModuleService.getById(id);
        if (jnAdaptiveModule == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(jnAdaptiveModule);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param jnAdaptiveModule
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, JnAdaptiveModule jnAdaptiveModule) {
        return super.exportXls(request, jnAdaptiveModule, JnAdaptiveModule.class, "江南大屏模块");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, JnAdaptiveModule.class);
    }

}
