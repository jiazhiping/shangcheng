package com.taotao.search.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.TaoResult;
import com.taotao.manager.pojo.Item;
import com.taotao.search.service.SearchService;

@Controller
@RequestMapping("search")
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Value("${TAOTAO_SEARCH_ROWS}")
	private Integer rows;

	// http://search.taotao.com/search.html?q=apple
	/**
	 * 商品搜索
	 * 
	 * @param model
	 * @param q
	 * @param page
	 * @return
	 */
	@RequestMapping
	public String search(Model model, String q, @RequestParam(value = "page", defaultValue = "1") Integer page) {
		try {
			// 重新编码解决get请求的乱码问题
			// q = new String(q.getBytes("ISO-8859-1"), "UTF-8");
			q = new String(q.getBytes("ISO-8859-1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 调用搜索服务，从索引库中查询商品
		TaoResult<Item> taoResult = this.searchService.search(q, page, this.rows);

		// 页面需要4个数据,把数据放到Model中，传递给前台页面
		// 搜索关键词
		model.addAttribute("query", q);
		// 商品查询结果集
		model.addAttribute("itemList", taoResult.getRows());
		// 当前页码数
		model.addAttribute("page", page);
		// 总页数
		model.addAttribute("totalPages", (taoResult.getTotal() + this.rows - 1) / this.rows);

		return "search";
	}

}
