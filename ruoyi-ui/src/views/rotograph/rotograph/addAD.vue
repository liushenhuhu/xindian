<template>
  <div>
    <el-form ref="elForm" :model="formData" :rules="rules" size="medium" label-width="100px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="formData.title" placeholder="请输入标题" clearable :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item label="摘要" prop="digest">
        <el-input v-model="formData.digest" type="textarea" placeholder="请输入摘要"
                  :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item label="跳转位置" prop="tlocation">
        <el-select v-model="formData.tlocation" placeholder="请选择跳转位置" clearable :style="{width: '100%'}">
          <el-option v-for="(item, index) in tlocationOptions" :key="index" :label="item.label"
                     :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="链接ID" prop="id">
        <el-input v-model="formData.id" placeholder="请输入链接ID" clearable :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item label="日期范围" prop="time">
        <el-date-picker type="daterange" v-model="formData.time" format="yyyy-MM-dd"
                        value-format="yyyy-MM-dd" :style="{width: '100%'}" start-placeholder="开始日期" end-placeholder="结束日期"
                        range-separator="至" clearable></el-date-picker>
      </el-form-item>
      <el-form-item label="显示位置" prop="xlocation">
        <el-select v-model="formData.xlocation" placeholder="请选择显示位置" clearable :style="{width: '100%'}">
          <el-option v-for="(item, index) in xlocationOptions" :key="index" :label="item.label"
                     :value="item.value" :disabled="item.disabled"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item size="large">
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  components: {},
  props: [],
  data() {
    return {
      formData: {
        title: undefined,
        digest: undefined,
        tlocation: undefined,
        id: undefined,
        time: null,
        xlocation: undefined,
      },
      rules: {
        title: [{
          required: true,
          message: '请输入标题',
          trigger: 'blur'
        }],
        digest: [{
          required: true,
          message: '请输入摘要',
          trigger: 'blur'
        }],
        tlocation: [{
          required: true,
          message: '请选择跳转位置',
          trigger: 'change'
        }],
        id: [{
          required: true,
          message: '请输入链接ID',
          trigger: 'blur'
        }],
        time: [{
          required: true,
          message: '日期范围不能为空',
          trigger: 'change'
        }],
        xlocation: [{
          required: true,
          message: '请选择显示位置',
          trigger: 'change'
        }],
      },
      tlocationOptions: [{
        "label": "one",
        "value": 1
      }, {
        "label": "two",
        "value": 2
      }, {
        "label": "three",
        "value": 3
      }],
      xlocationOptions: [{
        "label": "选项一",
        "value": 1
      }, {
        "label": "选项二",
        "value": 2
      }],
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}

</script>
<style>
</style>
