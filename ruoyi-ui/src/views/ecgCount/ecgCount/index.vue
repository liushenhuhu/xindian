<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="110px">
      <el-form-item label="记录时间" prop="recordDate">
        <el-date-picker clearable
                        v-model="queryParams.recordDate"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="请选择记录时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="年龄" prop="age" >
        <el-input
          v-model="queryParams.age"
          placeholder="请输入年龄"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <el-select placeholder="请选择性别" v-model="queryParams.gender" clearable >
          <el-option label="男" value="男"></el-option>
          <el-option label="女" value="女"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="导联数" prop="leadCount">
        <el-select placeholder="请选择导联数" v-model="queryParams.leadCount" clearable >
          <el-option label="12" value="12"></el-option>
          <el-option label="18" value="18"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="异常类型状态" prop="leadCount">
        <el-cascader :options="option"  v-model="ecgTypeValue"></el-cascader>
      </el-form-item>
<!--      <el-form-item label="正常心电图" prop="zcxdtEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.zcxdtEcg"-->
<!--          placeholder="请输入正常心电图"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="窦性心律" prop="dxxlEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.dxxlEcg"-->
<!--          placeholder="请输入窦性心律"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="异位心律" prop="ywxlEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.ywxlEcg"-->
<!--          placeholder="请输入异位心律"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="房性心律" prop="fxxlEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.fxxlEcg"-->
<!--          placeholder="请输入房性心律"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="室性心律" prop="sxxlEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.sxxlEcg"-->
<!--          placeholder="请输入室性心律"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="交界性心律" prop="jjxxlEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.jjxxlEcg"-->
<!--          placeholder="请输入交界性心律"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="起搏心律" prop="qbxlEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.qbxlEcg"-->
<!--          placeholder="请输入起搏心律"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="室性心动过速" prop="sxxdgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.sxxdgsEcg"-->
<!--          placeholder="请输入室性心动过速"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="室上性心动过速" prop="ssxxdgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.ssxxdgsEcg"-->
<!--          placeholder="请输入室上性心动过速"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="交界性心动过速" prop="jjxxdgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.jjxxdgsEcg"-->
<!--          placeholder="请输入交界性心动过速"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="房性心动过速" prop="fxxdgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.fxxdgsEcg"-->
<!--          placeholder="请输入房性心动过速"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="房性逸搏" prop="fxybEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.fxybEcg"-->
<!--          placeholder="请输入房性逸搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="房性早搏" prop="fxzbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.fxzbEcg"-->
<!--          placeholder="请输入房性早搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="房性停搏" prop="fxtbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.fxtbEcg"-->
<!--          placeholder="请输入房性停搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="室性逸搏" prop="sxybEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.sxybEcg"-->
<!--          placeholder="请输入室性逸搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="室性早搏" prop="sxzbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.sxzbEcg"-->
<!--          placeholder="请输入室性早搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="交界性逸搏" prop="jjxybEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.jjxybEcg"-->
<!--          placeholder="请输入交界性逸搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="交界性早搏" prop="jjxzbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.jjxzbEcg"-->
<!--          placeholder="请输入交界性早搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="交界性停搏" prop="jjxtbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.jjxtbEcg"-->
<!--          placeholder="请输入交界性停搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="窦性停搏" prop="dxtbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.dxtbEcg"-->
<!--          placeholder="请输入窦性停搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="心室停搏" prop="xstbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xstbEcg"-->
<!--          placeholder="请输入心室停搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="全心停搏" prop="qxtbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.qxtbEcg"-->
<!--          placeholder="请输入全心停搏"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="心房颤动" prop="xfcdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xfcdEcg"-->
<!--          placeholder="请输入心房颤动"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="心房扑动" prop="xfpdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xfpdEcg"-->
<!--          placeholder="请输入心房扑动"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="心室颤动" prop="xscdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xscdEcg"-->
<!--          placeholder="请输入心室颤动"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="心室扑动" prop="xspdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xspdEcg"-->
<!--          placeholder="请输入心室扑动"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="一度房室阻滞" prop="ydfszdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.ydfszdEcg"-->
<!--          placeholder="请输入一度房室阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="二度房室阻滞" prop="edfszdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.edfszdEcg"-->
<!--          placeholder="请输入二度房室阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="二度I型房室阻滞" prop="edixfszdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.edixfszdEcg"-->
<!--          placeholder="请输入二度I型房室阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="二度II型房室阻滞" prop="ediixfszdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.ediixfszdEcg"-->
<!--          placeholder="请输入二度II型房室阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="三度房室阻滞" prop="sdfszdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.sdfszdEcg"-->
<!--          placeholder="请输入三度房室阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="窦房阻滞" prop="dfzdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.dfzdEcg"-->
<!--          placeholder="请输入窦房阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="高度房室阻滞" prop="gdfszdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.gdfszdEcg"-->
<!--          placeholder="请输入高度房室阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="左前分支阻滞" prop="zqfzzdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.zqfzzdEcg"-->
<!--          placeholder="请输入左前分支阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="左后分支阻滞" prop="zhfzzdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.zhfzzdEcg"-->
<!--          placeholder="请输入左后分支阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="完全性左束支阻滞" prop="wqxzszzdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.wqxzszzdEcg"-->
<!--          placeholder="请输入完全性左束支阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="不完全性左束支阻滞" prop="bwqxzszzdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.bwqxzszzdEcg"-->
<!--          placeholder="请输入不完全性左束支阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="完全性右束支阻滞" prop="wqxyszzdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.wqxyszzdEcg"-->
<!--          placeholder="请输入完全性右束支阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="不完全性右束支阻滞" prop="bwqxyszzdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.bwqxyszzdEcg"-->
<!--          placeholder="请输入不完全性右束支阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="非特异性室内阻滞" prop="ftyxsnzdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.ftyxsnzdEcg"-->
<!--          placeholder="请输入非特异性室内阻滞"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="室内差异性传导" prop="sncyxcdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.sncyxcdEcg"-->
<!--          placeholder="请输入室内差异性传导"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="心室预激波" prop="xsyjbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xsyjbEcg"-->
<!--          placeholder="请输入心室预激波"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="预激综合症" prop="yjzhzEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.yjzhzEcg"-->
<!--          placeholder="请输入预激综合症"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="左心房肥大" prop="zxffdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.zxffdEcg"-->
<!--          placeholder="请输入左心房肥大"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="左心室肥大" prop="zxsfdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.zxsfdEcg"-->
<!--          placeholder="请输入左心室肥大"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="右心房肥大" prop="yxffdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.yxffdEcg"-->
<!--          placeholder="请输入右心房肥大"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="右心室肥大" prop="yxsfdEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.yxsfdEcg"-->
<!--          placeholder="请输入右心室肥大"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="ST改变" prop="stgbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.stgbEcg"-->
<!--          placeholder="请输入ST改变"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="ST-T改变" prop="st-tgbEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.st-tgbEcg"-->
<!--          placeholder="请输入ST-T改变"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="T波异常" prop="tbycEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.tbycEcg"-->
<!--          placeholder="请输入T波异常"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="QT间期延长" prop="qtjqycEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.qtjqycEcg"-->
<!--          placeholder="请输入QT间期延长"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="QTc间期延长" prop="qtcjqycEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.qtcjqycEcg"-->
<!--          placeholder="请输入QTc间期延长"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="可见U波" prop="kjubEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.kjubEcg"-->
<!--          placeholder="请输入可见U波"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="早期复极" prop="zqfjEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.zqfjEcg"-->
<!--          placeholder="请输入早期复极"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="前间壁心肌梗死" prop="qjbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.qjbxjgsEcg"-->
<!--          placeholder="请输入前间壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="前侧壁心肌梗死" prop="qcbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.qcbxjgsEcg"-->
<!--          placeholder="请输入前侧壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="广泛前壁心肌梗死" prop="qgfbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.qgfbxjgsEcg"-->
<!--          placeholder="请输入广泛前壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="前壁心肌梗死" prop="qbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.qbxjgsEcg"-->
<!--          placeholder="请输入前壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="下壁心肌梗死" prop="xbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xbxjgsEcg"-->
<!--          placeholder="请输入下壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="下后壁心肌梗死" prop="xhbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xhbxjgsEcg"-->
<!--          placeholder="请输入下后壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="后壁心肌梗死" prop="hbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.hbxjgsEcg"-->
<!--          placeholder="请输入后壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="下侧壁心肌梗死" prop="xcbbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xcbbxjgsEcg"-->
<!--          placeholder="请输入下侧壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="高侧壁心肌梗死" prop="gcbbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.gcbbxjgsEcg"-->
<!--          placeholder="请输入高侧壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="侧壁心肌梗死" prop="cbbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.cbbxjgsEcg"-->
<!--          placeholder="请输入侧壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="心内膜下心肌梗死" prop="xnmxxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xnmxxjgsEcg"-->
<!--          placeholder="请输入心内膜下心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="右室心肌梗死" prop="ysxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.ysxjgsEcg"-->
<!--          placeholder="请输入右室心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="非ST抬高心肌梗死" prop="fsttgxxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.fsttgxxjgsEcg"-->
<!--          placeholder="请输入非ST抬高心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="心尖部心肌梗死" prop="xjbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xjbxjgsEcg"-->
<!--          placeholder="请输入心尖部心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="间壁心肌梗死" prop="jbxjgsEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.jbxjgsEcg"-->
<!--          placeholder="请输入间壁心肌梗死"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="亚急性" prop="yjxEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.yjxEcg"-->
<!--          placeholder="请输入亚急性"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="急性" prop="jxEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.jxEcg"-->
<!--          placeholder="请输入急性"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="陈旧性" prop="cjxEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.cjxEcg"-->
<!--          placeholder="请输入陈旧性"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="心电图危急值" prop="xdtwjzEcg">-->
<!--        <el-input-->
<!--          v-model="queryParams.xdtwjzEcg"-->
<!--          placeholder="请输入心电图危急值"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['system:count:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:count:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:count:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:count:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="countList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="${comment}" align="center" prop="id" />-->
      <el-table-column label="记录时间" align="center" prop="recordDate" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.recordDate, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="年龄" align="center" prop="age" />
      <el-table-column label="性别" align="center" prop="gender" />
      <el-table-column label="导联数" align="center" prop="leadCount" />
      <el-table-column label="正常心电图" align="center" prop="zcxdtEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.zcxdtEcg===0" type="danger">否</el-tag>
          <el-tag v-else-if="scope.row.zcxdtEcg===1">是</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="窦性心律" align="center" prop="dxxlEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.dxxlEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.dxxlEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="异位心律" align="center" prop="ywxlEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ywxlEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.ywxlEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="房性心律" align="center" prop="fxxlEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.fxxlEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.fxxlEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="室性心律" align="center" prop="sxxlEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sxxlEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.sxxlEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="交界性心律" align="center" prop="jjxxlEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.jjxxlEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.jjxxlEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="起搏心律" align="center" prop="qbxlEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.qbxlEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.qbxlEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="室性心动过速" align="center" prop="sxxdgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sxxdgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.sxxdgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="室上性心动过速" align="center" prop="ssxxdgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ssxxdgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.ssxxdgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="交界性心动过速" align="center" prop="jjxxdgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.jjxxdgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.jjxxdgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="房性心动过速" align="center" prop="fxxdgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.fxxdgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.fxxdgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="房性逸搏" align="center" prop="fxybEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.fxybEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.fxybEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="房性早搏" align="center" prop="fxzbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.fxzbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.fxzbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="房性停搏" align="center" prop="fxtbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.fxtbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.fxtbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="室性逸搏" align="center" prop="sxybEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sxybEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.sxybEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="室性早搏" align="center" prop="sxzbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sxzbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.sxzbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="交界性逸搏" align="center" prop="jjxybEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.jjxybEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.jjxybEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="交界性早搏" align="center" prop="jjxzbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.jjxzbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.jjxzbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="交界性停搏" align="center" prop="jjxtbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.jjxtbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.jjxtbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="窦性停搏" align="center" prop="dxtbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.dxtbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.dxtbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="心室停搏" align="center" prop="xstbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xstbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xstbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="全心停搏" align="center" prop="qxtbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.qxtbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.qxtbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="心房颤动" align="center" prop="xfcdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xfcdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xfcdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="心房扑动" align="center" prop="xfpdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xfpdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xfpdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="心室颤动" align="center" prop="xscdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xscdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xscdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="心室扑动" align="center" prop="xspdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xspdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xspdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="一度房室阻滞" align="center" prop="ydfszdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ydfszdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.ydfszdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="二度房室阻滞" align="center" prop="edfszdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.edfszdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.edfszdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="二度I型房室阻滞" align="center" prop="edixfszdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.edixfszdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.edixfszdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="二度II型房室阻滞" align="center" prop="ediixfszdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ediixfszdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.ediixfszdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="三度房室阻滞" align="center" prop="sdfszdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sdfszdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.sdfszdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="窦房阻滞" align="center" prop="dfzdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.dfzdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.dfzdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="高度房室阻滞" align="center" prop="gdfszdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.gdfszdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.gdfszdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="左前分支阻滞" align="center" prop="zqfzzdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.zqfzzdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.zqfzzdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="左后分支阻滞" align="center" prop="zhfzzdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.zhfzzdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.zhfzzdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="完全性左束支阻滞" align="center" prop="wqxzszzdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.wqxzszzdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.wqxzszzdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="不完全性左束支阻滞" align="center" prop="bwqxzszzdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.bwqxzszzdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.bwqxzszzdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="完全性右束支阻滞" align="center" prop="wqxyszzdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.wqxyszzdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.wqxyszzdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="不完全性右束支阻滞" align="center" prop="bwqxyszzdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.bwqxyszzdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.bwqxyszzdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="非特异性室内阻滞" align="center" prop="ftyxsnzdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ftyxsnzdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.ftyxsnzdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="室内差异性传导" align="center" prop="sncyxcdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sncyxcdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.sncyxcdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="心室预激波" align="center" prop="xsyjbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xsyjbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xsyjbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="预激综合症" align="center" prop="yjzhzEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.yjzhzEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.yjzhzEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="左心房肥大" align="center" prop="zxffdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.zxffdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.zxffdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="左心室肥大" align="center" prop="zxsfdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.zxsfdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.zxsfdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="右心房肥大" align="center" prop="yxffdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.yxffdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.yxffdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="右心室肥大" align="center" prop="yxsfdEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.yxsfdEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.yxsfdEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="ST改变" align="center" prop="stgbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.stgbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.stgbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="ST-T改变" align="center" prop="stTgbEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.stTgbEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.stTgbEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="T波异常" align="center" prop="tbycEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.tbycEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.tbycEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="QT间期延长" align="center" prop="qtjqycEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.qtjqycEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.qtjqycEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="QTc间期延长" align="center" prop="qtcjqycEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.qtcjqycEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.qtcjqycEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="可见U波" align="center" prop="kjubEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.kjubEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.kjubEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="早期复极" align="center" prop="zqfjEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.zqfjEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.zqfjEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="前间壁心肌梗死" align="center" prop="qjbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.qjbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.qjbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="前侧壁心肌梗死" align="center" prop="qcbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.qcbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.qcbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="广泛前壁心肌梗死" align="center" prop="qgfbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.qgfbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.qgfbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="前壁心肌梗死" align="center" prop="qbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.qbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.qbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="下壁心肌梗死" align="center" prop="xbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="下后壁心肌梗死" align="center" prop="xhbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xhbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xhbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="后壁心肌梗死" align="center" prop="hbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.hbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.hbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="下侧壁心肌梗死" align="center" prop="xcbbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xcbbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xcbbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="高侧壁心肌梗死" align="center" prop="gcbbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.gcbbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.gcbbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="侧壁心肌梗死" align="center" prop="cbbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.cbbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.cbbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="心内膜下心肌梗死" align="center" prop="xnmxxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xnmxxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xnmxxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="右室心肌梗死" align="center" prop="ysxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.ysxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.ysxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="非ST抬高心肌梗死" align="center" prop="fsttgxxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.fsttgxxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.fsttgxxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="心尖部心肌梗死" align="center" prop="xjbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xjbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xjbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="间壁心肌梗死" align="center" prop="jbxjgsEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.jbxjgsEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.jbxjgsEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="亚急性" align="center" prop="yjxEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.yjxEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.yjxEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="急性" align="center" prop="jxEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.jxEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.jxEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="陈旧性" align="center" prop="cjxEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.cjxEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.cjxEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="心电图危急值" align="center" prop="xdtwjzEcg" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.xdtwjzEcg===1" type="danger">是</el-tag>
          <el-tag v-else-if="scope.row.xdtwjzEcg===0">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:count:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:count:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="记录时间" prop="recordDate">
          <el-date-picker clearable
                          v-model="form.recordDate"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="请选择记录时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-input v-model="form.gender" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="统计" prop="leadCount">
          <el-input v-model="form.leadCount" placeholder="请输入统计" />
        </el-form-item>
        <el-form-item label="正常心电图" prop="zcxdtEcg">
          <el-input v-model="form.zcxdtEcg" placeholder="请输入正常心电图" />
        </el-form-item>
        <el-form-item label="窦性心律" prop="dxxlEcg">
          <el-input v-model="form.dxxlEcg" placeholder="请输入窦性心律" />
        </el-form-item>
        <el-form-item label="异位心律" prop="ywxlEcg">
          <el-input v-model="form.ywxlEcg" placeholder="请输入异位心律" />
        </el-form-item>
        <el-form-item label="房性心律" prop="fxxlEcg">
          <el-input v-model="form.fxxlEcg" placeholder="请输入房性心律" />
        </el-form-item>
        <el-form-item label="室性心律" prop="sxxlEcg">
          <el-input v-model="form.sxxlEcg" placeholder="请输入室性心律" />
        </el-form-item>
        <el-form-item label="交界性心律" prop="jjxxlEcg">
          <el-input v-model="form.jjxxlEcg" placeholder="请输入交界性心律" />
        </el-form-item>
        <el-form-item label="起搏心律" prop="qbxlEcg">
          <el-input v-model="form.qbxlEcg" placeholder="请输入起搏心律" />
        </el-form-item>
        <el-form-item label="室性心动过速" prop="sxxdgsEcg">
          <el-input v-model="form.sxxdgsEcg" placeholder="请输入室性心动过速" />
        </el-form-item>
        <el-form-item label="室上性心动过速" prop="ssxxdgsEcg">
          <el-input v-model="form.ssxxdgsEcg" placeholder="请输入室上性心动过速" />
        </el-form-item>
        <el-form-item label="交界性心动过速" prop="jjxxdgsEcg">
          <el-input v-model="form.jjxxdgsEcg" placeholder="请输入交界性心动过速" />
        </el-form-item>
        <el-form-item label="房性心动过速" prop="fxxdgsEcg">
          <el-input v-model="form.fxxdgsEcg" placeholder="请输入房性心动过速" />
        </el-form-item>
        <el-form-item label="房性逸搏" prop="fxybEcg">
          <el-input v-model="form.fxybEcg" placeholder="请输入房性逸搏" />
        </el-form-item>
        <el-form-item label="房性早搏" prop="fxzbEcg">
          <el-input v-model="form.fxzbEcg" placeholder="请输入房性早搏" />
        </el-form-item>
        <el-form-item label="房性停搏" prop="fxtbEcg">
          <el-input v-model="form.fxtbEcg" placeholder="请输入房性停搏" />
        </el-form-item>
        <el-form-item label="室性逸搏" prop="sxybEcg">
          <el-input v-model="form.sxybEcg" placeholder="请输入室性逸搏" />
        </el-form-item>
        <el-form-item label="室性早搏" prop="sxzbEcg">
          <el-input v-model="form.sxzbEcg" placeholder="请输入室性早搏" />
        </el-form-item>
        <el-form-item label="交界性逸搏" prop="jjxybEcg">
          <el-input v-model="form.jjxybEcg" placeholder="请输入交界性逸搏" />
        </el-form-item>
        <el-form-item label="交界性早搏" prop="jjxzbEcg">
          <el-input v-model="form.jjxzbEcg" placeholder="请输入交界性早搏" />
        </el-form-item>
        <el-form-item label="交界性停搏" prop="jjxtbEcg">
          <el-input v-model="form.jjxtbEcg" placeholder="请输入交界性停搏" />
        </el-form-item>
        <el-form-item label="窦性停搏" prop="dxtbEcg">
          <el-input v-model="form.dxtbEcg" placeholder="请输入窦性停搏" />
        </el-form-item>
        <el-form-item label="心室停搏" prop="xstbEcg">
          <el-input v-model="form.xstbEcg" placeholder="请输入心室停搏" />
        </el-form-item>
        <el-form-item label="全心停搏" prop="qxtbEcg">
          <el-input v-model="form.qxtbEcg" placeholder="请输入全心停搏" />
        </el-form-item>
        <el-form-item label="心房颤动" prop="xfcdEcg">
          <el-input v-model="form.xfcdEcg" placeholder="请输入心房颤动" />
        </el-form-item>
        <el-form-item label="心房扑动" prop="xfpdEcg">
          <el-input v-model="form.xfpdEcg" placeholder="请输入心房扑动" />
        </el-form-item>
        <el-form-item label="心室颤动" prop="xscdEcg">
          <el-input v-model="form.xscdEcg" placeholder="请输入心室颤动" />
        </el-form-item>
        <el-form-item label="心室扑动" prop="xspdEcg">
          <el-input v-model="form.xspdEcg" placeholder="请输入心室扑动" />
        </el-form-item>
        <el-form-item label="一度房室阻滞" prop="ydfszdEcg">
          <el-input v-model="form.ydfszdEcg" placeholder="请输入一度房室阻滞" />
        </el-form-item>
        <el-form-item label="二度房室阻滞" prop="edfszdEcg">
          <el-input v-model="form.edfszdEcg" placeholder="请输入二度房室阻滞" />
        </el-form-item>
        <el-form-item label="二度I型房室阻滞" prop="edixfszdEcg">
          <el-input v-model="form.edixfszdEcg" placeholder="请输入二度I型房室阻滞" />
        </el-form-item>
        <el-form-item label="二度II型房室阻滞" prop="ediixfszdEcg">
          <el-input v-model="form.ediixfszdEcg" placeholder="请输入二度II型房室阻滞" />
        </el-form-item>
        <el-form-item label="三度房室阻滞" prop="sdfszdEcg">
          <el-input v-model="form.sdfszdEcg" placeholder="请输入三度房室阻滞" />
        </el-form-item>
        <el-form-item label="窦房阻滞" prop="dfzdEcg">
          <el-input v-model="form.dfzdEcg" placeholder="请输入窦房阻滞" />
        </el-form-item>
        <el-form-item label="高度房室阻滞" prop="gdfszdEcg">
          <el-input v-model="form.gdfszdEcg" placeholder="请输入高度房室阻滞" />
        </el-form-item>
        <el-form-item label="左前分支阻滞" prop="zqfzzdEcg">
          <el-input v-model="form.zqfzzdEcg" placeholder="请输入左前分支阻滞" />
        </el-form-item>
        <el-form-item label="左后分支阻滞" prop="zhfzzdEcg">
          <el-input v-model="form.zhfzzdEcg" placeholder="请输入左后分支阻滞" />
        </el-form-item>
        <el-form-item label="完全性左束支阻滞" prop="wqxzszzdEcg">
          <el-input v-model="form.wqxzszzdEcg" placeholder="请输入完全性左束支阻滞" />
        </el-form-item>
        <el-form-item label="不完全性左束支阻滞" prop="bwqxzszzdEcg">
          <el-input v-model="form.bwqxzszzdEcg" placeholder="请输入不完全性左束支阻滞" />
        </el-form-item>
        <el-form-item label="完全性右束支阻滞" prop="wqxyszzdEcg">
          <el-input v-model="form.wqxyszzdEcg" placeholder="请输入完全性右束支阻滞" />
        </el-form-item>
        <el-form-item label="不完全性右束支阻滞" prop="bwqxyszzdEcg">
          <el-input v-model="form.bwqxyszzdEcg" placeholder="请输入不完全性右束支阻滞" />
        </el-form-item>
        <el-form-item label="非特异性室内阻滞" prop="ftyxsnzdEcg">
          <el-input v-model="form.ftyxsnzdEcg" placeholder="请输入非特异性室内阻滞" />
        </el-form-item>
        <el-form-item label="室内差异性传导" prop="sncyxcdEcg">
          <el-input v-model="form.sncyxcdEcg" placeholder="请输入室内差异性传导" />
        </el-form-item>
        <el-form-item label="心室预激波" prop="xsyjbEcg">
          <el-input v-model="form.xsyjbEcg" placeholder="请输入心室预激波" />
        </el-form-item>
        <el-form-item label="预激综合症" prop="yjzhzEcg">
          <el-input v-model="form.yjzhzEcg" placeholder="请输入预激综合症" />
        </el-form-item>
        <el-form-item label="左心房肥大" prop="zxffdEcg">
          <el-input v-model="form.zxffdEcg" placeholder="请输入左心房肥大" />
        </el-form-item>
        <el-form-item label="左心室肥大" prop="zxsfdEcg">
          <el-input v-model="form.zxsfdEcg" placeholder="请输入左心室肥大" />
        </el-form-item>
        <el-form-item label="右心房肥大" prop="yxffdEcg">
          <el-input v-model="form.yxffdEcg" placeholder="请输入右心房肥大" />
        </el-form-item>
        <el-form-item label="右心室肥大" prop="yxsfdEcg">
          <el-input v-model="form.yxsfdEcg" placeholder="请输入右心室肥大" />
        </el-form-item>
        <el-form-item label="ST改变" prop="stgbEcg">
          <el-input v-model="form.stgbEcg" placeholder="请输入ST改变" />
        </el-form-item>
        <el-form-item label="ST-T改变" prop="st-tgbEcg">
          <el-input v-model="form.stTgbEcg" placeholder="请输入ST-T改变" />
        </el-form-item>
        <el-form-item label="T波异常" prop="tbycEcg">
          <el-input v-model="form.tbycEcg" placeholder="请输入T波异常" />
        </el-form-item>
        <el-form-item label="QT间期延长" prop="qtjqycEcg">
          <el-input v-model="form.qtjqycEcg" placeholder="请输入QT间期延长" />
        </el-form-item>
        <el-form-item label="QTc间期延长" prop="qtcjqycEcg">
          <el-input v-model="form.qtcjqycEcg" placeholder="请输入QTc间期延长" />
        </el-form-item>
        <el-form-item label="可见U波" prop="kjubEcg">
          <el-input v-model="form.kjubEcg" placeholder="请输入可见U波" />
        </el-form-item>
        <el-form-item label="早期复极" prop="zqfjEcg">
          <el-input v-model="form.zqfjEcg" placeholder="请输入早期复极" />
        </el-form-item>
        <el-form-item label="前间壁心肌梗死" prop="qjbxjgsEcg">
          <el-input v-model="form.qjbxjgsEcg" placeholder="请输入前间壁心肌梗死" />
        </el-form-item>
        <el-form-item label="前侧壁心肌梗死" prop="qcbxjgsEcg">
          <el-input v-model="form.qcbxjgsEcg" placeholder="请输入前侧壁心肌梗死" />
        </el-form-item>
        <el-form-item label="广泛前壁心肌梗死" prop="qgfbxjgsEcg">
          <el-input v-model="form.qgfbxjgsEcg" placeholder="请输入广泛前壁心肌梗死" />
        </el-form-item>
        <el-form-item label="前壁心肌梗死" prop="qbxjgsEcg">
          <el-input v-model="form.qbxjgsEcg" placeholder="请输入前壁心肌梗死" />
        </el-form-item>
        <el-form-item label="下壁心肌梗死" prop="xbxjgsEcg">
          <el-input v-model="form.xbxjgsEcg" placeholder="请输入下壁心肌梗死" />
        </el-form-item>
        <el-form-item label="下后壁心肌梗死" prop="xhbxjgsEcg">
          <el-input v-model="form.xhbxjgsEcg" placeholder="请输入下后壁心肌梗死" />
        </el-form-item>
        <el-form-item label="后壁心肌梗死" prop="hbxjgsEcg">
          <el-input v-model="form.hbxjgsEcg" placeholder="请输入后壁心肌梗死" />
        </el-form-item>
        <el-form-item label="下侧壁心肌梗死" prop="xcbbxjgsEcg">
          <el-input v-model="form.xcbbxjgsEcg" placeholder="请输入下侧壁心肌梗死" />
        </el-form-item>
        <el-form-item label="高侧壁心肌梗死" prop="gcbbxjgsEcg">
          <el-input v-model="form.gcbbxjgsEcg" placeholder="请输入高侧壁心肌梗死" />
        </el-form-item>
        <el-form-item label="侧壁心肌梗死" prop="cbbxjgsEcg">
          <el-input v-model="form.cbbxjgsEcg" placeholder="请输入侧壁心肌梗死" />
        </el-form-item>
        <el-form-item label="心内膜下心肌梗死" prop="xnmxxjgsEcg">
          <el-input v-model="form.xnmxxjgsEcg" placeholder="请输入心内膜下心肌梗死" />
        </el-form-item>
        <el-form-item label="右室心肌梗死" prop="ysxjgsEcg">
          <el-input v-model="form.ysxjgsEcg" placeholder="请输入右室心肌梗死" />
        </el-form-item>
        <el-form-item label="非ST抬高心肌梗死" prop="fsttgxxjgsEcg">
          <el-input v-model="form.fsttgxxjgsEcg" placeholder="请输入非ST抬高心肌梗死" />
        </el-form-item>
        <el-form-item label="心尖部心肌梗死" prop="xjbxjgsEcg">
          <el-input v-model="form.xjbxjgsEcg" placeholder="请输入心尖部心肌梗死" />
        </el-form-item>
        <el-form-item label="间壁心肌梗死" prop="jbxjgsEcg">
          <el-input v-model="form.jbxjgsEcg" placeholder="请输入间壁心肌梗死" />
        </el-form-item>
        <el-form-item label="亚急性" prop="yjxEcg">
          <el-input v-model="form.yjxEcg" placeholder="请输入亚急性" />
        </el-form-item>
        <el-form-item label="急性" prop="jxEcg">
          <el-input v-model="form.jxEcg" placeholder="请输入急性" />
        </el-form-item>
        <el-form-item label="陈旧性" prop="cjxEcg">
          <el-input v-model="form.cjxEcg" placeholder="请输入陈旧性" />
        </el-form-item>
        <el-form-item label="心电图危急值" prop="xdtwjzEcg">
          <el-input v-model="form.xdtwjzEcg" placeholder="请输入心电图危急值" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listCount, getCount, delCount, addCount, updateCount, getLogType} from "@/api/ecgCount/ecgCount";

export default {
  name: "Count",
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
      ecgTypeValue:null,
      // 【请填写功能名称】表格数据
      countList: [],
      // 弹出层标题
      title: "",
      option:[],
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        recordDate: null,
        age: null,
        gender: null,
        leadCount: null,
        zcxdtEcg: null,
        dxxlEcg: null,
        ywxlEcg: null,
        fxxlEcg: null,
        sxxlEcg: null,
        jjxxlEcg: null,
        qbxlEcg: null,
        sxxdgsEcg: null,
        ssxxdgsEcg: null,
        jjxxdgsEcg: null,
        fxxdgsEcg: null,
        fxybEcg: null,
        fxzbEcg: null,
        fxtbEcg: null,
        sxybEcg: null,
        sxzbEcg: null,
        jjxybEcg: null,
        jjxzbEcg: null,
        jjxtbEcg: null,
        dxtbEcg: null,
        xstbEcg: null,
        qxtbEcg: null,
        xfcdEcg: null,
        xfpdEcg: null,
        xscdEcg: null,
        xspdEcg: null,
        ydfszdEcg: null,
        edfszdEcg: null,
        edixfszdEcg: null,
        ediixfszdEcg: null,
        sdfszdEcg: null,
        dfzdEcg: null,
        gdfszdEcg: null,
        zqfzzdEcg: null,
        zhfzzdEcg: null,
        wqxzszzdEcg: null,
        bwqxzszzdEcg: null,
        wqxyszzdEcg: null,
        bwqxyszzdEcg: null,
        ftyxsnzdEcg: null,
        sncyxcdEcg: null,
        xsyjbEcg: null,
        yjzhzEcg: null,
        zxffdEcg: null,
        zxsfdEcg: null,
        yxffdEcg: null,
        yxsfdEcg: null,
        stgbEcg: null,
        stTgbEcg: null,
      tbycEcg: null,
      qtjqycEcg: null,
      qtcjqycEcg: null,
      kjubEcg: null,
      zqfjEcg: null,
      qjbxjgsEcg: null,
      qcbxjgsEcg: null,
      qgfbxjgsEcg: null,
      qbxjgsEcg: null,
      xbxjgsEcg: null,
      xhbxjgsEcg: null,
      hbxjgsEcg: null,
      xcbbxjgsEcg: null,
      gcbbxjgsEcg: null,
      cbbxjgsEcg: null,
      xnmxxjgsEcg: null,
      ysxjgsEcg: null,
      fsttgxxjgsEcg: null,
      xjbxjgsEcg: null,
      jbxjgsEcg: null,
      yjxEcg: null,
      jxEcg: null,
      cjxEcg: null,
      xdtwjzEcg: null
  },
    // 表单参数
    form: {},
    // 表单校验
    rules: {
    }
  };
  },
  created() {
    getLogType().then(r=>{
      this.option = r.data
    })
    this.getList();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      if (this.ecgTypeValue!=null&&this.ecgTypeValue!==''){
        let text = this.ecgTypeValue[0]+'Ecg'
        this.queryParams[text] = this.ecgTypeValue[1]
      }

      listCount(this.queryParams).then(response => {
        this.countList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        recordDate: null,
        age: null,
        gender: null,
        leadCount: null,
        zcxdtEcg: null,
        dxxlEcg: null,
        ywxlEcg: null,
        fxxlEcg: null,
        sxxlEcg: null,
        jjxxlEcg: null,
        qbxlEcg: null,
        sxxdgsEcg: null,
        ssxxdgsEcg: null,
        jjxxdgsEcg: null,
        fxxdgsEcg: null,
        fxybEcg: null,
        fxzbEcg: null,
        fxtbEcg: null,
        sxybEcg: null,
        sxzbEcg: null,
        jjxybEcg: null,
        jjxzbEcg: null,
        jjxtbEcg: null,
        dxtbEcg: null,
        xstbEcg: null,
        qxtbEcg: null,
        xfcdEcg: null,
        xfpdEcg: null,
        xscdEcg: null,
        xspdEcg: null,
        ydfszdEcg: null,
        edfszdEcg: null,
        edixfszdEcg: null,
        ediixfszdEcg: null,
        sdfszdEcg: null,
        dfzdEcg: null,
        gdfszdEcg: null,
        zqfzzdEcg: null,
        zhfzzdEcg: null,
        wqxzszzdEcg: null,
        bwqxzszzdEcg: null,
        wqxyszzdEcg: null,
        bwqxyszzdEcg: null,
        ftyxsnzdEcg: null,
        sncyxcdEcg: null,
        xsyjbEcg: null,
        yjzhzEcg: null,
        zxffdEcg: null,
        zxsfdEcg: null,
        yxffdEcg: null,
        yxsfdEcg: null,
        stgbEcg: null,
        stTgbEcg: null,
        tbycEcg: null,
        qtjqycEcg: null,
        qtcjqycEcg: null,
        kjubEcg: null,
        zqfjEcg: null,
        qjbxjgsEcg: null,
        qcbxjgsEcg: null,
        qgfbxjgsEcg: null,
        qbxjgsEcg: null,
        xbxjgsEcg: null,
        xhbxjgsEcg: null,
        hbxjgsEcg: null,
        xcbbxjgsEcg: null,
        gcbbxjgsEcg: null,
        cbbxjgsEcg: null,
        xnmxxjgsEcg: null,
        ysxjgsEcg: null,
        fsttgxxjgsEcg: null,
        xjbxjgsEcg: null,
        jbxjgsEcg: null,
        yjxEcg: null,
        jxEcg: null,
        cjxEcg: null,
        xdtwjzEcg: null
    };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.ecgTypeValue=null
      console.log(this.ecgTypeValue)
      this.queryParams = {
          recordDate: null,
          age: null,
          gender: null,
          leadCount: null,
          zcxdtEcg: null,
          dxxlEcg: null,
          ywxlEcg: null,
          fxxlEcg: null,
          sxxlEcg: null,
          jjxxlEcg: null,
          qbxlEcg: null,
          sxxdgsEcg: null,
          ssxxdgsEcg: null,
          jjxxdgsEcg: null,
          fxxdgsEcg: null,
          fxybEcg: null,
          fxzbEcg: null,
          fxtbEcg: null,
          sxybEcg: null,
          sxzbEcg: null,
          jjxybEcg: null,
          jjxzbEcg: null,
          jjxtbEcg: null,
          dxtbEcg: null,
          xstbEcg: null,
          qxtbEcg: null,
          xfcdEcg: null,
          xfpdEcg: null,
          xscdEcg: null,
          xspdEcg: null,
          ydfszdEcg: null,
          edfszdEcg: null,
          edixfszdEcg: null,
          ediixfszdEcg: null,
          sdfszdEcg: null,
          dfzdEcg: null,
          gdfszdEcg: null,
          zqfzzdEcg: null,
          zhfzzdEcg: null,
          wqxzszzdEcg: null,
          bwqxzszzdEcg: null,
          wqxyszzdEcg: null,
          bwqxyszzdEcg: null,
          ftyxsnzdEcg: null,
          sncyxcdEcg: null,
          xsyjbEcg: null,
          yjzhzEcg: null,
          zxffdEcg: null,
          zxsfdEcg: null,
          yxffdEcg: null,
          yxsfdEcg: null,
          stgbEcg: null,
          stTgbEcg: null,
          tbycEcg: null,
          qtjqycEcg: null,
          qtcjqycEcg: null,
          kjubEcg: null,
          zqfjEcg: null,
          qjbxjgsEcg: null,
          qcbxjgsEcg: null,
          qgfbxjgsEcg: null,
          qbxjgsEcg: null,
          xbxjgsEcg: null,
          xhbxjgsEcg: null,
          hbxjgsEcg: null,
          xcbbxjgsEcg: null,
          gcbbxjgsEcg: null,
          cbbxjgsEcg: null,
          xnmxxjgsEcg: null,
          ysxjgsEcg: null,
          fsttgxxjgsEcg: null,
          xjbxjgsEcg: null,
          jbxjgsEcg: null,
          yjxEcg: null,
          jxEcg: null,
          cjxEcg: null,
          xdtwjzEcg: null
      }
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加【请填写功能名称】";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCount(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改【请填写功能名称】";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCount(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCount(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + ids + '"的数据项？').then(function() {
        return delCount(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/count/export', {
        ...this.queryParams
      }, `count_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
