import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

export default function UserList() {
	const navigator = useNavigate();

	let [users, setUsers] = useState([
		{
			fullName: "",
			expiration: "",
			accountNonExpired: "",
			role: "",
		},
	]);

	const authenticationToken = localStorage.getItem("authentication_token");

	useEffect(() => {
		fetch("http://localhost:8080/api/v1/customers", {
			headers: {
				Authorization: `Bearer ${authenticationToken}`,
			},
		})
			.then((response) => response.json())
			.then((response) => setUsers(response))
			.catch(() => {
				window.alert("Usuário não autenticado ou bloqueado");
				navigator("/login");
			});
	}, []);

	return (
		<>
			<div className="card text-center position-absolute top-50 start-50 translate-middle text-center shadow-lg p-2">
				<table className="table table-striped">
					<thead>
						<tr>
							<th scope="col">Nome</th>
							<th scope="col">Expiração</th>
							<th scope="col">Expirado</th>
							<th scope="col">Função</th>
						</tr>
					</thead>
					<tbody>
						{users.map((user, i) => (
							<tr key={i}>
								<td>{user.fullName}</td>
								<td>{user.expiration === null ? "Vazio" : user.expiration}</td>
								<td>{(!user.accountNonExpired).toString()}</td>
								<td>{user.role}</td>
							</tr>
						))}
					</tbody>
				</table>
			</div>
		</>
	);
}
