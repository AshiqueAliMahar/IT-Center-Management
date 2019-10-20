<div class="mt-5">
            
            <div style="text-align: center;">
            <img src="data:image/gif;base64,${image}" alt="Not Found" class="img-fluid rounded-circle m-auto" id="img" >
            </div>
            <form action="updateProfile" method="post" enctype="multipart/form-data">
                <input type="file" onchange="readURL(this)"  accept="image/png, image/jpeg" class="btn btn-outline-success form-control-file m-2" name="image">
                <input type="text" name="name" placeholder="Name" class="form-control m-2" value="${name}">
                <input type="text" name="cnic" placeholder="CNIC" class="form-control m-2" value="${cnic }">
                <button name="update" value="${email }" class="btn btn-block btn-success m-2">Modify</button>
            </form>
        </div>
    <script>

        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function(e) {
                    $('#img').attr('src', e.target.result);
                    /* document.getElementByClassName('blah').setAttribute('src',e.target.result); */
                };

                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>