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

                    <!-- Basic Card -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary">Add User</h6>
                        </div>

                        <%-- Form --%>
                        <div class="card-body">
                            <form class="user" method="POST">

                                <input type="hidden" name="id" value="${requestScope.userToEdit.id}"/>
                                <div class="col-sm-5 mb-3 mb-sm-0">
                                    <div class="form-group">
                                        <label for="UsernameInput">Edit username:</label>
                                        <input type="text"
                                            class="form-control form-control-user"
                                            id="usernameInput"
                                            value="${requestScope.userToEdit.userName}"
                                            name="username"
                                            placeholder="Username"
                                        />
                                    </div>
                                    <div class="form-group">
                                        <label for="EmailInput">Edit email:</label>
                                        <input type="email"
                                               class="form-control form-control-user"
                                               id="EmailInput"
                                               value="${requestScope.userToEdit.email}"
                                               name="email"
                                               placeholder="Email Address"
                                        />
                                    </div>
                                    <div class="form-group">
                                        <label for="passwordInput">Edit password:</label>
                                        <input type="password"
                                            class="form-control form-control-user"
                                            id="passwordInput"
                                            name="password"
                                            placeholder="Password"
                                        />
                                    </div>
                                    <button type="submit" class="btn btn-primary btn-user btn-block">
                                        Edit
                                    </button>
                                </div>

                            </form>
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
