package com.taotao.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.AbstractDocument.Content;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMapMethodArgumentResolver;

import com.taotao.manager.pojo.ContentCategory;
import com.taotao.manager.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService {

	/**
	 * 查询
	 */
	@Override
	public List<ContentCategory> queryContentCategoryByParentId(Long parentId) {
		// 设置查询的条件
		ContentCategory contentCategory = new ContentCategory();
		contentCategory.setParentId(parentId);

		// 执行查询
		List<ContentCategory> list = super.queryListByWhere(contentCategory);
		return list;
	}

	/**
	 * 添加
	 */
	@Override
	public ContentCategory saveContentCategory(ContentCategory contentCategory) {
		// 设置内容分类数据
		contentCategory.setIsParent(false);
		contentCategory.setStatus(1);

		// 执行添加
		super.save(contentCategory);

		// 查询父节点
		ContentCategory parent = super.queryById(contentCategory.getParentId());
		// 判断父节点的isParent是否为false
		if (!parent.getIsParent()) {
			// 如果不为true 改为 false
			parent.setIsParent(true);
			// 修改父节点
			super.updateByIdSelective(parent);
			

		}
		return contentCategory;
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteContentCategory(Long parentId, Long id) {
		// 声明容器 , 存放需要删除的节点的Id
		ArrayList<Object> ids = new ArrayList<>();
		// 存放到容器
		ids.add(id);

		// 使用递归获取所有需要删除的节点的id，放到容器中
		getIds(id, ids);

		// 使用父的批量删除方法
		super.deleteByIds(ids);

		// 查询是否有兄弟节点
		ContentCategory contentCategory = new ContentCategory();
		contentCategory.setParentId(parentId);
		// 执行查询
		int count = super.queryCountByWhere(contentCategory);

		// 判断兄弟节点数量是否为0
		if (count == 0) {
			// 如果没有兄弟节点，设置父节点的isParent为false
			// 声明父节点并设置isParent
			ContentCategory parent = new ContentCategory();
			parent.setId(parentId);
			parent.setIsParent(false);

			super.updateByIdSelective(parent);

		}
	}

	// 递归查询节点的所有子节点
	private void getIds(Long id, List<Object> ids) {
		// 把参数id作为父id查询子节点
		ContentCategory param = new ContentCategory();
		param.setParentId(id);

		// 执行查询
		List<ContentCategory> list = super.queryListByWhere(param);

		// 判断查询到的子节点是否为0。如果使用递归，必须要有停止递归条件，否则会出现内存溢出
		if (list.size() > 0) {
			// 如果不为0，表示有子节点，进行递归
			// 遍历查询结果
			for (ContentCategory son : list) {
				// 把遍历的子节点的id放到ids容器中
				ids.add(son.getId());

				// 递归调用获取子节点的id,其实就是自己调自己
				this.getIds(son.getId(), ids);

			}
		}

		// 如果为0，表示没有子节点，停止递归,其实就是神马都不干，方法结束

	}

}
