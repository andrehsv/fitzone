import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Login from "./pages/Login";
import User from "./pages/User";
import UserList from "./pages/UserList";

function App() {
	return (
		<>
			<BrowserRouter>
				<Routes>
					<Route path="/">
						<Route index element={<Login />} />
						<Route path="login" element={<Login />} />
						<Route path="user" element={<User />} />
						<Route path="users" element={<UserList />} />
						<Route path="*" element={<h1>Error 404</h1>} />
					</Route>
				</Routes>
			</BrowserRouter>
		</>
	);
}

export default App;
