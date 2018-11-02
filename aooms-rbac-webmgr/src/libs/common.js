/**
 * 增删改查公共方法封装
 */
import { mapState, mapActions } from 'vuex'

let common = {};

/**
 * 获取用户信息
 */
common.userInfo = function(vm){
    return vm.$store.state.d2admin.user.info;
}

export default common