package com.mp.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;


@Data
@TableName("user") //指定表
public class User {
	@TableId //指定主键
	private Long id;
	@TableField("name") //指定字段
	private String name; 
	private String email;
	private Integer age;
	private Integer managerId;
	private LocalDateTime createTime;
	@TableField(exist=false) //增加不存在的字段
	private String remark;
}
