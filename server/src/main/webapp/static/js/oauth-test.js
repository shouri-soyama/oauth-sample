(function () { "use strict";
	var gBtn = $('.google-btn');
	var tBtn = $('.twitter-btn');
	gBtn.asEventStream('click').assign(function(){
		$.get('/api/goauth');
	});
	tBtn.asEventStream('click').assign(function(){
	});
})();
