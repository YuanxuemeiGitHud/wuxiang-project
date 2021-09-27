<template>
 <div>
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
       <k-form-build :value="formData" ref="kfb"/>
<!--       <builder :formData="formData" :formInfo="formInfo"></builder>-->
       <a-divider />
       <a-form layout="Vertical">
         <a-form-item
           label="processId">
           <a-input placeholder="请输入流程id..." v-model="processId" />
         </a-form-item>
       </a-form>
       <a-divider />
       <span class="span-style">使用过该表单的数据：</span>
       <a-table :columns="columns" :data-source="tableData" bordered style="margin-top: 10px" :scroll="{ x: 'calc(400px + 50%)', y: 240 }">
       </a-table>
     </a-modal>
   </div>
 </div>

</template>

<script>

import { addTableData, getAllForms, getColumnsByTableName, getTableByTableName } from '@api/formDesigner'
import Builder from '@views/formDesigner/builder/builder'
import { delFormById, getFormById } from '@api/formDesigner'
export default {
  name: "myForms",
  components: { Builder },
  data () {
    return {
      columns:[],
      Title:'',
      visible:false,
      formData:{},
      dataSource:[],//所有表单数据
      formInfo: {  },//表结构信息
      tableData:{}, //表中数据信息
      processId:'',

    }
  },
  /*props声明从父组件传过来的值*/
  props:[
    'dataSource',
  ],
  methods:{
    //获取表单结构信息，构造表头
    getFormInfos(){
      this.columns=[];
      var tableName={"tableName": this.Title};
      getColumnsByTableName(tableName).then((res)=>{
        if(res.success){
          this.formInfo=res.result;
          this.columns.push(
            {
              title: '序号',
              align: 'center',
              width:60,
              customRender: (text, record, index) => `${index + 1}` // 显示每一行的序号
            }
          )
          for( var k in this.formInfo) {
            if (k !== 'id') {
              var columnItem = {
                title: k,
                dataIndex: k,
                key: k,
                width: 100,
              }
              this.columns.push(columnItem)
            }
            else {

            }
          }
        }
        else{
          alert(res.message);
        }
      }).catch((err)=>{
        alert(err);
      })
    },
    //获取所有表单数据
    initData(){
      getAllForms().then((res)=>{
        if(res.success){
          this.dataSource = res.result;
        }else{
          this.$message.error({ content: "加载失败", key, duration: 2 });
        }
      }).catch((err) => {
        this.$message.error({ content: err, duration: 2 });
      });
    },
    toDesign(){
      this.$router.push({ name: 'formDesigner-designer-designer' })
    },
    //点击查看
    show(id){
      var param={'id':id}
      getFormById(param).then((res)=>{
        if(res.success){
          this.formData = JSON.parse(res.result.formJson);
          this.Title =res.result.formName;
          this.visible=true;
          this.getFormInfos();
          this.getFormAllData();
        }
        else{
          this.$message.error({ content: res.message, duration: 2 });
        }
      }).catch((error)=>{
        this.$message.error({ content: error, duration: 2 });
      });


    },
    //根据id删除表单
    delFormById(id){

      var param={'id':id}
      this.$confirm({
        title: '你想要删除此表单吗?',
        content: '你确定要删除此表单吗?删除后不可恢复',
        okText: '确认',
        okType: 'danger',
        cancelText: '取消',
        onOk:()=>{
          delFormById(param).then((res)=>{
            if(res.success){
              this.initData();
              this.$message.success({ content: res.message, duration: 2 });
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
      this.Title='';
      this.columns=[];
    },
    //表单输入后提交确定
    handleOk(){
      var tableData={}
      this.$refs.kfb.getData().then(values => {
        var a=1
        //将键转化成对应数据库表中的字段
        for(var i in values ){
            var key = i.split('_')[0]+'_'+a;
            this.$set(tableData, key, values[i]);
            a++;
        }

      }).catch(() => {
        console.log('验证未通过，获取失败')
      });
      this.formInfo={
        "tableName":this.Title,
        "tableFiled":tableData,
        "processId":this.processId
      }
      addTableData(this.formInfo).then((res)=>{
        if(res.success){
          alert("添加成功")
          //添加成功后重置表单数据
          this.$refs.kfb.reset();
          this.getFormAllData();
        }
        else{
          alert("添加失败")
        }
      }).catch((err)=>{
        alert(err)
      })
      this.formInfo={};
      this.processId='';
    },
    //获取表单的所有数据
    getFormAllData(){

      var tableName ={"tableName":this.Title}
      getTableByTableName(tableName).then((res)=>{
        if(res.success){
          this.tableData = res.result;
          console.log("表数据：")
          console.log(this.tableData)
          //根据一条数据构造表头
          }
        else{
          alert('获取数据失败')
        }
      }).catch((err)=>{
        console.log(err)
      })
    }
  },
  mounted(){
    this.initData();
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
.span-style{
  color: rgba(0,0,0,.85);
  font-size: 14px;
  line-height: 22px;
}
/*/deep/ element.style{
  width: 65%;
}*/
</style>