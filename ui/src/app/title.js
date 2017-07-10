angular
  .module('app')
  .component('app', {
    templateUrl: 'app/title.html',
    controllerAs: 'vm',
    controller: function ($log, $http, toastr) {
      const vm = this;
      const pattern = new RegExp('^(https?:\/\/)' + // protocol
        '((([a-z\d]([a-z\d-]*[a-z\d])*)\.)+[a-z]{2,}|' + // domain name
        '((\d{1,3}\.){3}\d{1,3}))' + // OR ip (v4) address
        '(:\d+)?(\/[-a-z\d%_.~+]*)*'); // port and path

      vm.fetchTitle = function (site) {
        if (!pattern.test(site)) {
          vm.form.site.$invalid = true;
          return;
        }

        $http({
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          url: '/api/title',
          data: {
            site: site
          }
        }).then(function (res) {
          vm.siteTitle = res.data.title;
        }, function (err) {
          vm.form.site.$invalid = true;
          toastr.error(err.data.message);
        });
      };
    }
  });
