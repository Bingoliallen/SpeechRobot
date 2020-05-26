package com.vma.speechrobot.bean;

/**
 * Created by Administrator on 2019/1/1.
 */

public class MessageTemplate {
   /* [
    {
        "id": 0,
            "is_all": 0,
            "module": 0,
            "name": "string",
            "type": 0
    }
]*/
    public int id;
    public int is_all;
    public int module;
    public String name;
    public int type;
    public boolean isSelect;

 public MessageTemplate(int id, int is_all, int module, String name, int type, boolean isSelect) {
  this.id = id;
  this.is_all = is_all;
  this.module = module;
  this.name = name;
  this.type = type;
  this.isSelect = isSelect;
 }
}
