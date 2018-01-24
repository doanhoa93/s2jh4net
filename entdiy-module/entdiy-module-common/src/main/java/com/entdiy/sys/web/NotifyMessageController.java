/**
 * Copyright © 2015 - 2017 EntDIY JavaEE Development Framework
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.entdiy.sys.web;

import com.entdiy.core.annotation.MenuData;
import com.entdiy.core.cons.GlobalConstant;
import com.entdiy.core.pagination.GroupPropertyFilter;
import com.entdiy.core.pagination.PropertyFilter;
import com.entdiy.core.service.BaseService;
import com.entdiy.core.util.EnumUtils;
import com.entdiy.core.web.BaseController;
import com.entdiy.core.web.view.OperationResult;
import com.entdiy.sys.entity.NotifyMessage;
import com.entdiy.sys.entity.NotifyMessage.NotifyMessagePlatformEnum;
import com.entdiy.sys.entity.NotifyMessageRead;
import com.entdiy.sys.service.DataDictService;
import com.entdiy.sys.service.NotifyMessageReadService;
import com.entdiy.sys.service.NotifyMessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/admin/sys/notify-message")
public class NotifyMessageController extends BaseController<NotifyMessage, Long> {

    @Autowired
    private NotifyMessageService notifyMessageService;

    @Autowired
    private NotifyMessageReadService notifyMessageReadService;

    @Autowired
    private DataDictService dataDictService;

    @Override
    protected BaseService<NotifyMessage, Long> getEntityService() {
        return notifyMessageService;
    }

    @RequiresUser
    @ModelAttribute
    public void prepareModel(HttpServletRequest request, Model model, @RequestParam(value = "id", required = false) Long id) {
        super.initPrepareModel(request, model, id);
    }

    @MenuData("配置管理:系统管理:公告管理")
    @RequiresPermissions("配置管理:系统管理:公告管理")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
        return "admin/sys/notifyMessage-index";
    }

    @RequiresPermissions("配置管理:系统管理:公告管理")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Page<NotifyMessage> findByPage(HttpServletRequest request) {
        return super.findByPage(NotifyMessage.class, request);
    }

    @RequiresPermissions("配置管理:系统管理:公告管理")
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editShow(Model model) {
        model.addAttribute("platformMap", EnumUtils.getEnumDataMap(NotifyMessagePlatformEnum.class));
        model.addAttribute("messageTypeMap", dataDictService.findMapDataByRootPrimaryKey(GlobalConstant.DATADICT_MESSAGE_TYPE));
        return "admin/sys/notifyMessage-inputBasic";
    }

    @RequiresPermissions("配置管理:系统管理:公告管理")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public OperationResult editSave(@ModelAttribute("entity") NotifyMessage entity, Model model) {
        return super.editSave(entity);
    }

    @RequiresPermissions("配置管理:系统管理:公告管理")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public OperationResult delete(@RequestParam("ids") Long... ids) {
        return super.delete(ids, (entity) -> notifyMessageReadService.countByNotifyMessage(entity) > 0 ? "公告[" + entity.getTitle() + "]已经被阅读，不能被删除" : null);
    }

    @RequiresPermissions("配置管理:系统管理:公告管理")
    @RequestMapping(value = "/read-list", method = RequestMethod.GET)
    @ResponseBody
    public Page<NotifyMessageRead> readList(HttpServletRequest request) {
        Pageable pageable = PropertyFilter.buildPageableFromHttpRequest(request);
        GroupPropertyFilter groupFilter = GroupPropertyFilter.buildFromHttpRequest(NotifyMessageRead.class, request);
        return notifyMessageReadService.findByPage(groupFilter, pageable);
    }

}