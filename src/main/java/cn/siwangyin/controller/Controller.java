package cn.siwangyin.controller;

import cn.siwangyin.dao.CMSDao;
import cn.siwangyin.domainObject.SwyCommodity;
import cn.siwangyin.domainObject.SwyManager;
import cn.siwangyin.domainObject.SwyNavType;
import cn.siwangyin.domainObject.SwyTag;
import cn.siwangyin.system.Context;
import cn.siwangyin.system.NameTextTag;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.json.Json;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwy on 2017/5/3.
 */
@RestController
public class Controller {

    private static Dao dao = CMSDao.getDao();

    @RequestMapping("/login")
    public Context Login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Context context = new Context();
        Condition cnd = Cnd.where("username", "=", username).and("password", "=", password);
        SwyManager sm = dao.fetch(SwyManager.class,cnd);
        context.setFlag(true);
        context.setObj(sm);
        return context;
    }

    @RequestMapping("/queryUser")
    public Context queryUser() {
        Context context = new Context();
        context.setFlag(true);
        context.setList(dao.query(SwyManager.class, null));
        return context;
    }

    @RequestMapping("/saveUser")
    public Context saveUser(@RequestParam("json") String json) {
        Context context = new Context();
        SwyManager manager = Json.fromJson(SwyManager.class, json);
        dao.update(manager);
        context.setObj(manager);
        context.setFlag(true);
        return context;
    }

    @RequestMapping("/addUser")
    public Context addUser(@RequestParam("json") String json) {
        Context context = new Context();
        SwyManager manager = Json.fromJson(SwyManager.class, json);
        manager = dao.insert(manager);
        context.setObj(manager);
        context.setFlag(true);
        return context;
    }

    @RequestMapping("/queryNav")
    public Context queryNav() {
        Context context = new Context();
        context.setFlag(true);
        context.setList(dao.query(SwyNavType.class, null));
        return context;
    }

    @RequestMapping("/queryNavTags")
    public Context queryNavTags() {
        Context context = new Context();
        Condition cnd = Cnd.where("state","=",'Y').and("parentId", "=", 0);
        context.setFlag(true);
        context.setList(dao.query(SwyTag.class, cnd));
        return context;
    }

    @RequestMapping("/saveNav")
    public Context saveNav(@RequestParam("json") String json, @RequestParam("tagIds") String tagIds) {
        Context context = new Context();
        SwyNavType type = Json.fromJson(SwyNavType.class, json);
        type.setTagIds(tagIds);
        dao.update(type);
        context.setObj(type);
        context.setFlag(true);
        return context;
    }

    @RequestMapping("/addNav")
    public Context addNav(@RequestParam("json") String json, @RequestParam("tagIds") String tagIds) {
        Context context = new Context();
        SwyNavType type = Json.fromJson(SwyNavType.class, json);
        type.setTagIds(tagIds);
        type = dao.insert(type);
        context.setObj(type);
        context.setFlag(true);
        return context;
    }

    @RequestMapping("/queryTag")
    public Context queryTag() {
        Context context = new Context();
        context.setFlag(true);
        context.setList(dao.query(SwyTag.class, null));
        return context;
    }

    @RequestMapping("/addTag")
    public Context addTag(@RequestParam("json") String json) {
        Context context = new Context();
        SwyTag tag = Json.fromJson(SwyTag.class, json);
        tag = dao.insert(tag);
        context.setObj(tag);
        context.setFlag(true);
        return context;
    }

    @RequestMapping("/saveTag")
    public Context saveTag(@RequestParam("json") String json) {
        Context context = new Context();
        SwyTag tag = Json.fromJson(SwyTag.class, json);
        dao.update(tag);
        context.setObj(tag);
        context.setFlag(true);
        return context;
    }

    @RequestMapping("/queryAllTags")
    public Context queryAllTags() {
        Context context = new Context();
        Condition cnd = Cnd.where("state", "=", 'Y');
        List<SwyNavType> list1 = dao.query(SwyNavType.class, cnd);
        List<SwyTag> list2 = dao.query(SwyTag.class, cnd);
        List<NameTextTag> list = new ArrayList<>();
        list1.forEach((SwyNavType type) -> {
            NameTextTag nameTextTag = new NameTextTag();
            nameTextTag.setName(type.getName());
            nameTextTag.setText(type.getText());
            list.add(nameTextTag);
        });
        list2.forEach((SwyTag tag) -> {
            NameTextTag nameTextTag = new NameTextTag();
            nameTextTag.setName(tag.getName());
            nameTextTag.setText(tag.getText());
            list.add(nameTextTag);
        });
        context.setFlag(true);
        context.setList(list);
        return context;
    }

    @RequestMapping("/queryCommodity")
    public Context queryCommodity() {
        Context context = new Context();
        context.setFlag(true);
        context.setList(dao.query(SwyCommodity.class, null));
        return context;
    }

    @RequestMapping("/addCommodity")
    public Context addCommodity(@RequestParam("json") String json, @RequestParam("detailHtml") String detailHtml) {
        Context context = new Context();
        SwyCommodity commodity = Json.fromJson(SwyCommodity.class, json);
        commodity.setDetailHtml(detailHtml);
        commodity = dao.insert(commodity);
        context.setObj(commodity);
        context.setFlag(true);
        return context;
    }

    @RequestMapping("/saveCommodity")
    public Context saveCommodity(@RequestParam("json") String json, @RequestParam("detailHtml") String detailHtml) {
        Context context = new Context();
        SwyCommodity commodity = Json.fromJson(SwyCommodity.class, json);
        commodity.setDetailHtml(detailHtml);
        dao.update(commodity);
        context.setObj(commodity);
        context.setFlag(true);
        return context;
    }
}
