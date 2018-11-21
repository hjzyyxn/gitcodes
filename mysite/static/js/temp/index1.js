/**
 * Created by lenovo on 7/26 0026.
 */
angular
    .module('indexPage', ['elasticui','ui.bootstrap'])
    .constant('euiHost', esUrl)
    //搜索框
    .controller('searchController',['$scope','$window','$http',function ($scope,$window,$http) {
        $scope.queryEs = {query:null};
        $scope.getPeople = function (val) {
            return $http.post('http://' + esUrl + '/organization/_search?size=10&from=0', {
                query: {
                    "match": {"_all": {"query": val}}
                }
            }).then(function (response) {
                return response.data.hits.hits;
            });
        };
        $scope.getTech = function (val) {
            return $http.post('http://' + esUrl + '/study/studyData/_search?size=10&from=0', {
                query: {
                    "match": {"name": {"query": val}}
                }
            }).then(function (response) {
                return response.data.hits.hits;
            });
        };
        $scope.getOrg = function (val) {
            return $http.post('http://' + esUrl + '/organization/_search?size=10&from=0', {
                query: {
                    "match": {"_all": {"query": val}}
                }
            }).then(function (response) {
                return response.data.hits.hits;
            });
        };
        $scope.changeFlag = function () {
            if($scope.flag){
                $scope.flag = 0;   //0表示机构
            } else {
                $scope.flag = 1;   //1表示人物
            }
        };
        $scope.doSearch = function () {
            if($scope.queryEs.query !== null){
                $window.open('/da/search/tech/list/detail/?uuid='+$scope.queryEs.query, '_self');
            } else {
                alert('请输入要查询的内容');
            }
        };
        $scope.keydown = function (e) {
            var keycode = window.event?e.keyCode:e.which;
            if(keycode==13){
                $scope.doSearch();
            }
        }
    }])
    //最近更新的人物
    .controller('peopleController', ['$scope', '$window', function ($scope, $window) {
        $scope.apacheUrl = apacheUrl;
        $scope.peopleDetail = function (uuid) {
            $window.open('/da/search/people/detail/?uuid=' + uuid, '_blank')
        };
    }
    ])
    //最近更新的机构
    .controller('orgController',['$scope','$window',function ($scope, $window) {
        $scope.apacheUrl = apacheUrl;
        $scope.orgDetail = function (uuid) {
            $window.open('/da/search/organization/detail/?uuid=' + uuid, '_blank')
        };
    }])
    //机器人领域的人
    .controller('robotPeopleController',['$scope','$window',function ($scope,$window) {
        $scope.apacheUrl = apacheUrl;
        $scope.peopleDetail = function(uuid){
            $window.open('/da/search/people/detail/?uuid='+uuid);
        };
    }])
    //机器人领域的机构
    .controller('robotOrgController',['$scope','$window',function ($scope,$window) {
        $scope.apacheUrl = apacheUrl;
        $scope.orgDetail = function(uuid){
            $window.open('/da/search/organization/detail/?uuid='+uuid);
        };
    }])
    //航天领域的人
    .controller('aerospacePeopleController',['$scope','$window',function ($scope,$window) {
        $scope.apacheUrl = apacheUrl;
        $scope.peopleDetail = function(uuid){
            $window.open('/da/search/people/detail/?uuid='+uuid);
        };
    }])
    //航天领域的机构
    .controller('aerospaceOrgController',['$scope','$window',function ($scope,$window) {
        $scope.apacheUrl = apacheUrl;
        $scope.orgDetail = function(uuid){
            $window.open('/da/search/organization/detail/?uuid='+uuid);
        };
    }])
    //显示总数目
    .controller('numController',['$scope','$window',function ($scope,$window) {
        $scope.pplAmount = pplAmount;
        $scope.orgAmount = orgAmount;
        $scope.patAmount = patAmount;
        $scope.fundAmount = fundAmount;
        $scope.pubAmount = pubAmount;
        $scope.pplSearch = function () {
            $window.open('/da/search/people/', '_self');
        };
        $scope.orgSearch = function () {
            $window.open('/da/search/organization/', '_self');
        }
    }])
    .controller('techCtrl', ['$scope', '$http','$window', function ($scope, $http,$window) {
    $scope.cards =[
        {'id':0, 'title':'无人驾驶', 'eng':'Unmanned Vehicle', 'link':'/da/search/tech/list/detail/?uuid=5211884f-b9c7-11e6-84cd-08d40caf30d3', 'img':'/static/tech/Unmanned Vehicle.jpg',
            'description':'无人机，或称无人载具是一种无搭载人员的载具。通常使用遥控、导引或自动驾驶来控制。可在科学研究、军事、休闲娱乐用途上使用。'},
        {'id':1, 'title':'量子通信', 'eng':'Quantum Teleportation', 'link':'/da/search/tech/list/detail/?uuid=755ae361-b9c7-11e6-a0b8-08d40caf30d3','img':'/static/tech/Quantum Teleportation.jpg',
            'description':'量子通信是指利用量子纠缠效应进行信息传递的一种新型的通讯方式。量子通讯是近二十年发展起来的新型交叉学科，是量子论和信息论相结合的新的研究领域。'},
        {'id':2, 'title':'虚拟现实', 'eng':'Virtual Reality', 'link':'/da/search/tech/list/detail/?uuid=9d4516c0-b9c7-11e6-b187-08d40caf30d3','img':'/static/tech/Virtual Reality.jpg',
            'description':'虚拟实境（英语：virtual reality，缩写为VR），简称虚拟技术，也称虚拟环境，是利用电脑模拟产生一个三维空间的虚拟世界，提供用户关于视觉等感官的模拟，让用户感觉仿佛身历其境，可以及时、没有限制地观察三维空间内的事物。'},
        {'id':3, 'title':'超级计算机', 'eng':'Super Computer', 'link':'/da/search/tech/list/detail/?uuid=c3361230-b9c7-11e6-b7d5-08d40caf30d3','img':'/static/tech/Super Computer.jpg',
            'description':'超级计算机指能够执行一般个人电脑无法处理的大资料量与高速运算的电脑，其基本组成组件与个人电脑的概念无太大差异，但规格与性能则强大许多。'},
        {'id':4, 'title':'3D打印', 'eng':'3D Printing', 'link':'/da/search/tech/list/detail/?uuid=d2f2160f-b9c7-11e6-b485-08d40caf30d3','img':'/static/tech/3D Printing.jpg',
            'description':'3D打印又称增量制造、积层制造（Additive Manufacturing，AM），可指任何打印三维物体的过程。'},
        {'id':5, 'title':'人工智能', 'eng':'Artificial Intelligence', 'link':'/da/search/tech/list/detail/?uuid=e287cd40-b9c7-11e6-a0aa-08d40caf30d3','img':'/static/tech/Artificial Intelligence.jpg',
            'description':'人工智能（英语：Artificial Intelligence, AI）亦称机器智能，是指由人工制造出来的系统所表现出来的智能。通常人工智能是指通过普通电脑实现的智能。该词同时也指研究这样的智能系统是否能够实现，以及如何实现的科学领域。'}
    ]
}]);



