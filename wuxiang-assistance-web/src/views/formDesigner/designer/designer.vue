<template>
  <div class="container">
<!--    :toolbars="toolbars"-->
    <k-form-design
      :title="title"
      @save="handleSave"
    ></k-form-design>
    <a-modal
      :title="ModalTitle"
      :visible="visible"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel"
    >
      <a-form>
        <a-form-item label="表单名称">
          <a-input :placeholder= ModalText v-model="tableName"/>
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import getValue from 'k-form-design'
import { addForm } from '@api/formDesigner'
import { Alert } from 'ant-design-vue'


export default {
  name: 'designer',
  data() {
    return {
      obj:{},
      jsonData:{},
      tableName:'',
      title: '表单设计器',
      ModalTitle:'保存我的表单',
      ModalText: '请输入要保存的表单名',
      visible: false,
      confirmLoading: false,
      /*toolbars: ['save', 'preview']*/
    }
  },
  methods: {
    handleSave(values) {
      this.jsonData = JSON.parse(values);
      alert(values)
      alert(this.jsonData)
      if(this.jsonData.list.length !== 0){
        this.visible = true;
      }
      else{
        this.$message.warning({ content: "您设计的表单中还没有添加控件哦！", duration: 2 });
      }
    },
    handleOk(){
      var key = 'save'
      this.confirmLoading = true;
      var _tableName = this.tableName;
      this.$set(this.jsonData,"tableName",_tableName)
      addForm(this.jsonData).then((res) => {
        if(res.success){
          this.$message.success({ content: '保存成功!', key, duration: 2 });
          this.visible = false;
          this.confirmLoading = false;
        }
        else{
          this.$message.error({ content: res.message, key, duration: 2 });
          this.visible = false;
          this.confirmLoading = false;

        }
      }).catch((err) => {
        this.visible = false;
         this.confirmLoading = false;
        this.$message.error({ content: err, key, duration: 2 });
      });
        this.jsonData={};
        this.tableName = ''
    },
    handleCancel(e) {
      this.visible = false;
      this.jsonData={};
      this.tableName = ''
    },
    }
}
</script>

<style scoped>
.container {
  width: 100%;
  height: calc(100vh - 71px);
}

.container div {
  max-height: calc(100vh - 80px);
}
</style>