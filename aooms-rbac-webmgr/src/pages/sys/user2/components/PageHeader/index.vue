<template>
    <el-form
      :inline="true"
      :model="form"
      :rules="rules"
      ref="form"
      size="mini"
      label-width="60px"
      style="margin-bottom: -18px;">

      <el-form-item label="状态" prop="type">
        <el-select
          v-model="form.type"
          placeholder="状态选择"
          style="width: 100px;">
          <el-option label="状态 1" value="1"/>
          <el-option label="状态 2" value="2"/>
          <el-option label="状态 3" value="3"/>
          <el-option label="状态 4" value="4"/>
          <el-option label="状态 5" value="5"/>
        </el-select>
      </el-form-item>

      <el-form-item label="用户" prop="user">
        <el-input
          v-model="form.user"
          placeholder="用户"
          style="width: 100px;"/>
      </el-form-item>

      <el-form-item label="卡密" prop="key">
        <el-input
          v-model="form.key"
          placeholder="卡密"
          style="width: 120px;"/>
      </el-form-item>

      <el-form-item label="备注" prop="note">
        <el-input
          v-model="form.note"
          placeholder="备注"
          style="width: 120px;"/>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          @click="handleFormSubmit">
          <d2-icon name="search"/>
          查询
        </el-button>
      </el-form-item>

      <el-form-item>
        <el-button
          @click="handleFormReset">
          <d2-icon name="refresh"/>
          重置
        </el-button>
      </el-form-item>

      <el-form-item>

        <!--<span class="el-dropdown-link" style="cursor:pointer;color:#409eff;" @click="showMore = !showMore">
          更多条件
          <i :class="['el-icon&#45;&#45;right', showMore?'el-icon-arrow-up':'el-icon-arrow-down']"></i>
        </span>-->

        <el-popover
                placement="bottom"
                trigger="click"
                width="460"
                popper-class="aooms-popper-condition"
        >

          <!-- content -->
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <!--<el-button type="primary" icon="el-icon-more-outline" plain circle style="margin-right: 10px;"></el-button>-->
              <span>更多条件</span>
            </div>

            <el-row>
              <el-col :span="12">
                <el-form-item label="用户" prop="user" >
                  <el-input
                          v-model="form.user"
                          placeholder="用户"
                          style="width:120px;"/>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="用户2" prop="user">
                  <el-input
                          v-model="form.user"
                          placeholder="用户2"
                          style="width:120px;"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="24">
                <el-form-item label="用户3" prop="user" >
                  <el-input
                          v-model="form.user"
                          placeholder="用户"
                          style="width:330px;"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-card>
          <!-- content -->

          <span class="el-dropdown-link" style="cursor:pointer;color:#409eff;" slot="reference">
            更多条件
            <i class="el-icon--right el-icon-arrow-down"></i>
          </span>
        </el-popover>

      </el-form-item>

      <div v-if="showMore">
        <el-form-item label="用户" prop="user">
          <el-input
            v-model="form.user"
            placeholder="用户"
            style="width: 270px;"/>
        </el-form-item>

        <el-form-item label="用户2" prop="user">
          <el-input
            v-model="form.user"
            placeholder="用户2"
            style="width: 310px;"/>
        </el-form-item>
      </div>

    </el-form>
</template>

<style lang="scss">
  .aooms-popper-condition{
    padding: 0px;
  }
</style>

<script>
export default {
  data () {
    return {
      form: {
        type: '1',
        user: 'FairyEver',
        key: '',
        note: ''
      },
      rules: {
        type: [ { required: true, message: '请选择一个状态', trigger: 'change' } ],
        user: [ { required: true, message: '请输入用户', trigger: 'change' } ]
      },
      showMore: false
    }
  },
  methods: {
    handleFormSubmit () {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$emit('submit', this.form)
        } else {
          this.$notify.error({
            title: '错误',
            message: '表单校验失败'
          })
          return false
        }
      })
    },
    handleFormReset () {
      this.$refs.form.resetFields()
    }
  }
}
</script>
