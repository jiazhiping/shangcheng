package com.taotao.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.manager.pojo.Item;
import com.taotao.manager.service.ItemService;

@Controller
@RequestMapping("item/interface")
public class ItemInterfaceController {

	@Autowired
	private ItemService itemService;

	// 查询 GET http:// manager.taotao.com/rest/item/interface/{id}
	// 加上 @ResponseBody和返回ResponseEntity效果是一样的,都会走转换器，把Item对象转为json格式的数据
	// 使用 @ResponseBody和返回ResponseEntity使用任意的一个即可

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	// @ResponseBody
	public ResponseEntity<Item> queryItemById(@PathVariable Long id) {
		try {
			Item item = itemService.queryById(id);
			// 查询成功，返回200
			// return ResponseEntity.status(HttpStatus.OK).body(item);
			// return ResponseEntity.ok().body(item);
			return ResponseEntity.ok(item);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 如果服务器错误，返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	// 新增 POST http://manager.taotao.com/rest/item/interface
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Item> saveItem(Item item) {
		try {
			itemService.save(item);
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// 如果服务器错误，返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

	}

	// 修改 PUT http://manager.taotao.com/rest/item/interface
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> updateItem(Item item) {
		try {
			this.itemService.updateByIdSelective(item);
			// 更新返回204
			return ResponseEntity.status(204).build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 如果服务器错误，返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	// 删除 DELETE http:// manager.taotao.com/rest/item/interface/{id}
	/**
	 * 根据id删除商品
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
		try {
			this.itemService.deleteById(id);
			// 删除成功返回204
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 如果服务器错误，返回500
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
