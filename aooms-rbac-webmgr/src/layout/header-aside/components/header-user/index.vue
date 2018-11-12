<template>
  <el-dropdown size="small" class="d2-mr">
    <span class="btn-text">你好 {{info.userName}}</span>
    <el-dropdown-menu slot="dropdown">

      <el-dropdown-item @click.native="userInfo">
        <d2-icon name="user" class="d2-mr-5"/>
        个人信息
      </el-dropdown-item>

      <el-dropdown-item @click.native="updatePassword">
        <d2-icon name="key" class="d2-mr-5"/>
        修改密码
      </el-dropdown-item>


      <el-dropdown-item @click.native="logOff" divided>
        <d2-icon name="power-off" class="d2-mr-5"/>
        注销
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  computed: {
    ...mapState('d2admin/user', [
      'info'
    ])
  },
  methods: {
    ...mapActions('d2admin/account', [
      'logout'
    ]),
    /**
     * @description 登出
     */
    logOff () {
      this.logout({
        vm: this,
        confirm: true
      })
    },

    userInfo () {
        this.$router.push({
            path: '/rbac/user-info'
        });
    },

    updatePassword () {
        this.$router.push({
            path: '/rbac/user-password'
        });
    }
  }
}
</script>
