+function(d){var b=function(f,e){this.$element=d(f);this.options=d.extend({},b.DEFAULTS,e);this.init()};b.VERSION="1.0.0";b.DEFAULTS={inputIcon:"fa-calendar",clearBtn:true,autoclose:true,todayBtn:true,language:"zh-CN",format:"yyyy-mm-dd"};b.prototype.init=function(){var e=this.$element;var g=this.options;if(e.attr("readonly")||e.attr("disabled")){return}if(g.minViewMode==undefined){g.minViewMode="days";if(g.format=="yyyy-mm"){g.minViewMode="months"}else{if(this.options.format=="yyyy"){g.minViewMode="years"}}}if(g.inputIcon){e.wrap('<div class="input-icon"></div>');e.before("<i class='fa "+g.inputIcon+"'></i>");e.attr("data-input-icon-done","true")}e.datepicker(g);var f=e.closest("form");if(f.size()&&f.data("validation")){e.on("hide",function(){e.valid()})}};function c(e){Util.assert(d.fn.datepicker,"依赖组件 datepicker 未引入");return this.each(function(){var h=d(this);var g=h.data("extDatePicker");var f=typeof e=="object"&&e;if(!g){h.data("extDatePicker",(g=new b(this,f)))}if(typeof e=="string"){g[e]()}})}var a=d.fn.extDatePicker;d.fn.extDatePicker=c;d.fn.extDatePicker.Constructor=b;d.fn.extDatePicker.noConflict=function(){d.fn.extDatePicker=a;return this};Global.addComponent({name:"ExtDatePicker",plugin:c,expr:'input[data-picker="date"],input[data-rule-date]'})}(jQuery);