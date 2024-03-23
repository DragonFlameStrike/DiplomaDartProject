import React from 'react';
import { Nav, NavItem, NavLink } from 'reactstrap';
import { useLocation } from 'react-router-dom';

const Header = props => {
    const location = useLocation();

    // Стили для сайдбара
    const sidebarStyle = {
        position: 'fixed', // Зафиксированное положение
        top: 0, // Располагаем сверху
        left: 0, // Располагаем слева
        width: '100px', // Ширина сайдбара
        height: '100%', // Высота сайдбара
        backgroundColor: '#333', // Цвет фона
        padding: '20px', // Внутренний отступ
        zIndex: 1000, // Поверх всех элементов
    };

    // Стили для элементов сайдбара
    const linkStyle = {
        color: '#fff', // Цвет текста
        fontSize: '18px', // Размер шрифта
        textDecoration: 'none', // Убираем подчеркивание
        display: 'block', // Блочный элемент
        marginBottom: '15px', // Внешний отступ снизу
        transition: 'background-color 0.3s', // Плавное изменение цвета при наведении
        borderRadius: '5px', // Закругляем углы
        backgroundColor: 'gray',
    };

    // Стиль для активной ссылки
    const activeLinkStyle = {
        ...linkStyle,
        backgroundColor: 'red', // Цвет фона активной ссылки
    };

    return (
        <header>
            <div style={sidebarStyle}>
                <Nav vertical>
                    <NavItem>
                        <NavLink
                            href="/"
                            style={location.pathname === "/" ? activeLinkStyle : linkStyle}
                        >
                            <img src="/result.png" alt="bouy icon"/>
                        </NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink
                            href="/stations/"
                            style={location.pathname === "/stations/" ? activeLinkStyle : linkStyle}
                        >
                            <img src="/bouy.png" alt="bouy icon"/>
                        </NavLink>
                    </NavItem>
                </Nav>
            </div>
        </header>
    );
};

export default Header;
