<template>
  <div>
    {{formInfo}}
    <k-form-build :value="formData" ref="kfb"/>
  </div>
</template>

<script>
import { getColumnsByTableName } from '@api/formDesigner'

export default {
  name: 'builder', /*表单构建组件，将表单设计器生成的json数据构建成表单*/
  props:['formData','formInfo'],
  data () {
    return {
    /*  jsonData: JSON.parse(this.formData),*/
     form:{},
    }
  },
  methods:{

  },
  mounted() {

  }

}
</script>

<style scoped>

</style>