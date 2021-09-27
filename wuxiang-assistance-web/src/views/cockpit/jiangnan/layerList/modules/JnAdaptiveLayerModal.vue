<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form-model ref="form" :model="model" :rules="validatorRules">
        <a-form-model-item label="父级节点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pid">
          <j-tree-select
            ref="treeSelect"
            placeholder="请选择父级节点"
            v-model="model.pid"
            dict="jn_adaptive_layer,title,id"
            pidField="pid"
            pidValue="0"
            hasChildField="has_child"
          >
          </j-tree-select>
        </a-form-model-item>
        <a-form-model-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="title">
          <a-input v-model="model.title" placeholder="请输入名称"></a-input>
        </a-form-model-item>
        <a-form-model-item label="图层类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="layerType">
          <j-dict-select-tag type="list" v-model="model.layerType"
                             dictCode="jn_adaptive_layer_type,type_title,type_code" placeholder="请选择图层类型" />
        </a-form-model-item>
        <a-form-model-item label="图层服务路径" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="url">
          <a-input v-model="model.url" placeholder="请输入图层服务路径"></a-input>
        </a-form-model-item>
        <a-form-model-item label="所属板块" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="module">
          <j-tree-select
            ref="treeSelect"
            placeholder="请选择所属板块"
            v-model="model.module"
            dict="jn_adaptive_module,title,id"
            pidValue="0"
          >
          </j-tree-select>
        </a-form-model-item>
        <a-form-model-item label="图例路径" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="figureUrl">
          <j-image-upload isMultiple v-model="model.figureUrl"></j-image-upload>
        </a-form-model-item>
        <a-form-model-item label="仰角" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pitch">
          <a-input-number v-model="model.pitch" placeholder="请输入仰角" style="width: 100%" />
        </a-form-model-item>
        <a-form-model-item label="缩放级别" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="zoom">
          <a-input-number v-model="model.zoom" placeholder="请输入缩放级别" style="width: 100%" />
        </a-form-model-item>
        <a-form-model-item label="经度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="longitude">
          <a-input-number v-model="model.longitude" placeholder="请输入经度" style="width: 100%" />
        </a-form-model-item>
        <a-form-model-item label="纬度" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="latitude">
          <a-input-number v-model="model.latitude" placeholder="请输入纬度" style="width: 100%" />
        </a-form-model-item>
        <a-form-model-item label="排序值" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sortOrder">
          <a-input-number v-model="model.sortOrder" placeholder="请输入排序值" style="width: 100%" />
        </a-form-model-item>
        <a-form-model-item label="默认显示" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="defaultShow">
          <j-dict-select-tag type="list" v-model="model.defaultShow" dictCode="yn" placeholder="请选择默认显示" />
        </a-form-model-item>

      </a-form-model>
    </a-spin>
  </j-modal>
</template>

<script>

import { httpAction } from '@/api/manage'
import { validateDuplicateValue } from '@/utils/util'

export default {
  name: 'JnAdaptiveLayerModal',
  components: {},
  data() {
    return {
      title: '操作',
      width: 800,
      visible: false,
      model: {},
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },

      confirmLoading: false,
      validatorRules: {
        pitch: [
          { required: false },
          { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!' }
        ],
        zoom: [
          { required: false },
          { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!' }
        ],
        longitude: [
          { required: false },
          { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!' }
        ],
        latitude: [
          { required: false },
          { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!' }
        ],
        sortOrder: [
          { required: false },
          { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!' }
        ]
      },
      url: {
        add: '/cockpit/jiangnan/jnAdaptiveLayer/add',
        edit: '/cockpit/jiangnan/jnAdaptiveLayer/edit'
      },
      expandedRowKeys: [],
      pidField: 'pid'

    }
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model))
  },
  methods: {
    add(obj) {
      this.modelDefault.pid = ''
      this.edit(Object.assign(this.modelDefault, obj))
    },
    edit(record) {
      this.model = Object.assign({}, record)
      this.visible = true
    },
    close() {
      this.$emit('close')
      this.visible = false
      this.$refs.form.clearValidate()
    },
    handleOk() {
      const that = this
      // 触发表单验证
      this.$refs.form.validate(valid => {
        if (valid) {
          that.confirmLoading = true
          let httpurl = ''
          let method = ''
          if (!this.model.id) {
            httpurl += this.url.add
            method = 'post'
          } else {
            httpurl += this.url.edit
            method = 'put'
          }
          if (this.model.id && this.model.id === this.model[this.pidField]) {
            that.$message.warning('父级节点不能选择自己')
            that.confirmLoading = false
            return
          }
          httpAction(httpurl, this.model, method).then((res) => {
            if (res.success) {
              that.$message.success(res.message)
              this.$emit('ok')
            } else {
              that.$message.warning(res.message)
            }
          }).finally(() => {
            that.confirmLoading = false
            that.close()
          })
        } else {
          return false
        }
      })
    },
    handleCancel() {
      this.close()
    },
    submitSuccess(formData, flag) {
      if (!formData.id) {
        let treeData = this.$refs.treeSelect.getCurrTreeData()
        this.expandedRowKeys = []
        this.getExpandKeysByPid(formData[this.pidField], treeData, treeData)
        this.$emit('ok', formData, this.expandedRowKeys.reverse())
      } else {
        this.$emit('ok', formData, flag)
      }
    },
    getExpandKeysByPid(pid, arr, all) {
      if (pid && arr && arr.length > 0) {
        for (let i = 0; i < arr.length; i++) {
          if (arr[i].key == pid) {
            this.expandedRowKeys.push(arr[i].key)
            this.getExpandKeysByPid(arr[i]['parentId'], all, all)
          } else {
            this.getExpandKeysByPid(pid, arr[i].children, all)
          }
        }
      }
    }

  }
}
</script>