function isPlaceholder(){
    var input = document.createElement('input');  
    return 'placeholder' in input;  
}  

if (!isPlaceholder()) {
    $(document).ready(function() {  
        if(!isPlaceholder()){  
            $("input").not("input[type='password']").each(
                function(){  
                    if($(this).val()=="" && $(this).attr("placeholder")!=""){  
                        $(this).val($(this).attr("placeholder"));  
                        $(this).focus(function(){  
                            if($(this).val()==$(this).attr("placeholder")) $(this).val("");  
                        });  
                        $(this).blur(function(){  
                            if($(this).val()=="") $(this).val($(this).attr("placeholder"));  
                        });  
                    }  
            });  

            $("input[type='password']").each(
            	function() {
            		var pwdField    = $(this);  
            		var pwdVal      = pwdField.attr('placeholder');
					pwdField.after('<input  class="login-input form-control" type="text" value="' + pwdVal + '" autocomplete="off" />');
            		var pwdPlaceholder = $(this).siblings('.login-input');  
            		pwdPlaceholder.show();  
            		pwdField.hide();  
            		  
            		pwdPlaceholder.focus(function(){  
            		    pwdPlaceholder.hide();  
            		    pwdField.show();  
            		    pwdField.focus();  
            		});  
            		  
            		pwdField.blur(function(){  
            		    if(pwdField.val() == '') {  
            		        pwdPlaceholder.show();  
            		        pwdField.hide();  
            		    }  
            		}); 
            	}
            )
              
        }  
    });  
      
}  