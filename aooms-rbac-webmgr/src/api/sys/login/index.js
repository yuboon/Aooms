import request from '@/plugin/axios'

export function AccountLogin (data) {
  return request({
    url: '/aooms/rbac/loginService/validateAccount',
    method: 'post',
    data
  })
}
