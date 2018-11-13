import request from '@/plugin/axios'

export function AccountLogin (data) {
  return request({
    url: '/aooms/rbac/loginService/login',
    method: 'post',
    data
  })
}
