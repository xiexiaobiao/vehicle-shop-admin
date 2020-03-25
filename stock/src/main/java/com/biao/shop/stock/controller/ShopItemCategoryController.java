package com.biao.shop.stock.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biao.shop.common.dto.ShopOrderAppDTO;
import com.biao.shop.common.entity.ShopItemCategoryEntity;
import com.biao.shop.common.enums.RespStatusEnum;
import com.biao.shop.common.response.ObjectResponse;
import com.biao.shop.stock.service.ShopItemCategoryService;
import org.dromara.soul.client.common.annotation.SoulClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品类别表 前端控制器
 * </p>
 *
 * @author XieXiaobiao
 * @since 2020-02-28
 */
@RestController
@RequestMapping("/stock")
public class ShopItemCategoryController {
    
   private ShopItemCategoryService categoryService;

   @Autowired
    public ShopItemCategoryController(ShopItemCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @SoulClient(path = "/vehicle/stock/category/save", desc = "新增类别")
    @PostMapping("/category/save")
    public ObjectResponse<Integer> createItemCate(@RequestBody ShopItemCategoryEntity categoryEntity){
        int result =  categoryService.createItemCate(categoryEntity);
        ObjectResponse<Integer> response = new ObjectResponse<>();
        response.setCode(RespStatusEnum.SUCCESS.getCode());
        response.setMessage(RespStatusEnum.SUCCESS.getMessage());
        response.setData(result);
        return response;

    }

    @SoulClient(path = "/vehicle/stock/max/categoryUId", desc = "查询最大类别id")
    @GetMapping("/max/categoryUId")
    public ObjectResponse<String> getMaxCateId(){
        String str =  categoryService.getMaxCateId();
        ObjectResponse<String> response = new ObjectResponse<>();
        response.setCode(RespStatusEnum.SUCCESS.getCode());
        response.setMessage(RespStatusEnum.SUCCESS.getMessage());
        response.setData(str);
        return response;
    }

    @SoulClient(path = "/vehicle/stock/category/**", desc = "根据id查询")
    @GetMapping("/category/{id}")
    public ShopItemCategoryEntity getItemCateById(@PathVariable("id") String id){
        return categoryService.getItemCateById(Integer.parseInt(id));
    }

    @SoulClient(path = "/vehicle/stock/category/", desc = "根据categoryId查询")
    @GetMapping("/category")
    public ShopItemCategoryEntity getItemCateByCateId(@RequestParam("categoryId") String categoryId){
        return categoryService.getItemCateByCateId(categoryId);
    }

    @SoulClient(path = "/vehicle/stock/category/list", desc = "获取类别列表")
    @GetMapping("/category/list")
    public ObjectResponse<Page<String>> listCate(@RequestParam("pageNum") int current, @RequestParam("pageSize")int size){
        Page<String> stringPage =  categoryService.listCate(current,size);
        ObjectResponse<Page<String>> response = new ObjectResponse<>();
        response.setCode(RespStatusEnum.SUCCESS.getCode());
        response.setMessage(RespStatusEnum.SUCCESS.getMessage());
        response.setData(stringPage);
        return response;
    }
}

