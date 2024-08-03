<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>

<html lang="en">

<head>
    <%@ include file="parts/meta.jsp" %>
    <title>User CRUD</title>
    <%@ include file="parts/links.jsp" %>
</head>

<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <%@ include file="parts/sidebar.jsp" %>

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <%@ include file="parts/topbar.jsp" %>

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <%@ include file="parts/header.jsp" %>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">User Details</h6>
                        </div>
                        <div class="card-body">
                            ${requestScope.userToShow.id}
                            <hr>
                            ${requestScope.userToShow.userName}
                            <hr>
                            ${requestScope.userToShow.email}
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

            <%@ include file="parts/footer.jsp" %>

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->

    <!-- Scroll to Top Button-->
    <a class="scroll-to-top rounded" href="#page-top">
        <i class="fas fa-angle-up"></i>
    </a>

    <%@ include file="parts/js-scripts.jsp" %>

</body>

</html>
