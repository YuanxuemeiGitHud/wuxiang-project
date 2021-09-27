<template>
  <div class="card-list" ref="content">
    <router-link :to="{ name: 'account-settings-Binding' }">
      去设计表单
    </router-link>
    <a-list
      :grid="{gutter: 24, lg: 3, md: 2, sm: 1, xs: 1}"
      :dataSource="dataSource"
    >
      <a-list-item slot="renderItem" slot-scope="item, index">
        <template v-if="index === 0">
          <a-button class="new-btn" type="dashed" @click="toDesign">
            <a-icon type="plus"/>
            设计表单
          </a-button>
        </template>
        <template v-else>
          <a-card :hoverable="true">
            <a-card-meta>
              <div style="margin-bottom: 3px" slot="title">{{ item.formName}}</div>
              <a-avatar class="card-avatar" slot="avatar"  size="large">
                <a-icon type="profile" />
              </a-avatar>
              <div class="meta-content" slot="description"></div>
            </a-card-meta>
            <template class="ant-card-actions" slot="actions">
              <a @click="show(item.id)"><a-icon type="eye"/> <span style="margin-left: 2px">查看</span> </a>
              <a @click="delFormById(item.id)"><a-icon type="delete"/><span style="margin-left: 2px">删除 </span></a>
            </template>
          </a-card>
        </template>
      </a-list-item>
    </a-list>
    <a-modal
      :title="Title"
      :visible="visible"
      @ok="handleOk"
      @cancel="handleCancel"
    >
     <builder :formData="formData"></builder>
    </a-modal>
  </div>
</template>

<script>

import Builder from '@views/formDesigner/builder/builder'
import { delFormById, getFormById } from '@api/formDesigner'
export default {
  name: "formCardList",
  components: { Builder },
  data () {
    return {
      Title:'',
      visible:false,
      formData:{},
    }
  },
  /*props声明从父组件传过来的值*/
  props:[
    'dataSource',
  ],
  methods:{
    toDesign(){
      this.$router.push({ name: 'formDesigner-designer-designer' })
    },
    show(id){
      var param={'id':id}
      getFormById(param).then((res)=>{
        if(res.success){
          this.formData = JSON.parse(res.result.formJson);
          this.Title =res.result.formName;
          this.visible=true;
        }
        else{
          this.$message.error({ content: res.message, duration: 2 });
        }
      }).catch((error)=>{
        this.$message.error({ content: error, duration: 2 });
      });
    },
    delFormById(id){
      var param={'id':id}
      this.$confirm({
        title: '你想要删除此表单吗?',
        content: '你确定要删除此表单吗?删除后不可恢复',
        onOk() {
          delFormById(param).then((res)=>{
            if(res.success){
              alert(JSON.stringify(res))
              alert(111)
              this.$message.success({ content: res.success, duration: 2 });
            }
            else{
              this.$message.error({ content: res.message, duration: 2 });
            }
          }).catch((error)=>{
            this.$message.error({ content: error, duration: 2 });
          });
        },
        onCancel() {

        },
      });
    },
    handleCancel(e) {
      this.visible = false;
      this.formData={};
      this.Title=''
    },
  }
}
</script>

<style lang="less" scoped>
.card-avatar {
  width: 48px;
  height: 48px;
  border-radius: 48px;
}

.ant-card-actions {
  background: #f7f9fa;
  li {
    float: left;
    text-align: center;
    margin: 12px 0;
    color: rgba(0, 0, 0, 0.45);
    width: 50%;

    &:not(:last-child) {
      border-right: 1px solid #e8e8e8;
    }

    a {
      color: rgba(0, 0, 0, .45);
      line-height: 22px;
      display: inline-block;
      width: 100%;
      &:hover {
        color: #1890ff;
      }
    }
  }
}

.new-btn {
  background-color: #fff;
  border-radius: 2px;
  width: 100%;
  height: 186px;
}

.meta-content {
  position: relative;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  height: 64px;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
</style>
