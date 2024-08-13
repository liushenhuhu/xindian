<template>
  <div class="app-container">

    <el-tabs type="border-card" @tab-click="handleClick" v-model="activeName" >
      <el-tab-pane label="待诊断数据" name="first">
        <el-table v-loading="loading" :data="reportList">
<!--          <el-table-column type="selection" width="55" align="center" />-->
<!--          <el-table-column label="报告id" align="center" prop="reportId" />-->
          <el-table-column label="心电id" align="center" prop="pId" />
          <el-table-column label="诊断状态" align="center" prop="diagnosisStatus">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.diagnosis_status" :value="scope.row.diagnosisStatus"/>
            </template>
          </el-table-column>
<!--          <el-table-column label="诊断结论" align="center" prop="diagnosisConclusion" show-overflow-tooltip/>-->
          <el-table-column label="诊断医生" align="center" prop="diagnosisDoctor" />
          <el-table-column label="报告时间" align="center" prop="reportTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.reportTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
<!--          <el-table-column label="智能诊断" align="center" prop="intelligentDiagnosis" show-overflow-tooltip/>-->
          <el-table-column label="报告种类" align="center" prop="reportType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.ecg_type" :value="scope.row.reportType"/>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleDiagnose(scope.row)"
                v-hasPermi="['report:report:edit']"
              >诊断</el-button>
            </template>
          </el-table-column>
        </el-table>


      </el-tab-pane>
      <el-tab-pane label="已诊断数据" name="second">已诊断数据</el-tab-pane>
    </el-tabs>

  </div>

</template>
<script>
import {getWebList, listReport} from "@/api/report/report";

export default {
  name: 'DoctorDiagnosis',
  dicts:['diagnosis_status','ecg_type'],
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      reportList:[],
      activeName: 'first',
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        pId: null,
        diagnosisStatus: null,
        diagnosisConclusion: null,
        diagnosisDoctor: null,
        reportTime: null,
        intelligentDiagnosis: null,
        pphone:null,
      },

    }
  },
  created() {
    this.queryParams.diagnosisStatus = '2';
    this.getList()
  },
  methods: {
    handleClick(tab, event) {
      if (activeName=='first'){
        this.queryParams.diagnosisStatus = '2';
        this.getList()
      }else {

      }
    },

    /** 查询报告列表 */
    getList() {
      this.loading = true;

      getWebList(this.queryParams).then(response => {
        this.reportList = response.rows;
        this.loading = false;
      });
    },
    handleDiagnose(row){
      if(row.reportType.includes('JECGsingle')){
        this.$router.push({
          path: "/staticECG",
          query: {pId: row.pId, state: 1, queryParams: this.queryParams, ecgType: "JECGsingle"}
        });
      }
      else if(row.reportType.includes('JECG4')){
        this.$router.push({path: "/JECG4_ECG", query: {pId: row.pId,state:4,queryParams:this.queryParams,ecgType:"JECG4"}});
      }
      else if(row.reportType.includes('JECG12')){
        this.$router.push({path: "/restingECG", query: {pId:row.pId,state:12,queryParams:this.queryParams,ecgType:"JECG12"}});
      } else if (row.reportType.includes('week')){
        this.$router.push({path: "/week_report", query: {pId:row.pId,pphone:row.pphone}});
      }
      else{
        this.$message.warning('未知类型，请联系管理员')
      }


    },

  },
}
</script>
<style scoped lang="scss">

::v-deep .el-tabs__item{
  color: #909399 !important;
}
::v-deep .is-active{
  color: #2e7dc9 !important;
}
</style>
